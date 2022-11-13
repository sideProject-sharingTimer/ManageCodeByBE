package com.sideproject.sharingtimer.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sideproject.sharingtimer.dto.KakaoUserInfoDto;
import com.sideproject.sharingtimer.model.User;
import com.sideproject.sharingtimer.repository.UserRepository;
import com.sideproject.sharingtimer.security.jwt.JwtTokenProvider;
import com.sideproject.sharingtimer.security.jwt.TokenDto;
import com.sideproject.sharingtimer.service.KakaoUserService;
import com.sideproject.sharingtimer.util.constants.MessageConstants;
import com.sideproject.sharingtimer.util.exception.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KakaoServiceImpl implements KakaoUserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private static final Logger logger = LoggerFactory.getLogger(KakaoServiceImpl.class);

    @Value("${client_id}")
    private String client_id;

    @Value("${redirect_uri}")
    private String redirect_uri;

    @Override
    @Transactional
    public ResponseDto kakaoLogin(String code) throws Exception {
        ResponseDto responseDto = new ResponseDto();

        try {
            // '인가 코드' 로 '엑세스 토큰' 요청.
            String accessToken = getAccessToken(code);
            // 토큰으로 카카오 API 호출
            KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);

            // DB 에 중복된 KakaoId 가 있는지 확인
            User kakaoUser = userRepository.findByKakaoId(kakaoUserInfo.getKakaoId()).orElse(null);

            //만약 아이디가 없다면 ? 등록 한다.
            if (kakaoUser == null) {
                registerKakaoUser(kakaoUserInfo);
            }
            // 등록후에는 ? ..
            kakaoUser = userRepository.findByKakaoId(kakaoUserInfo.getKakaoId()).orElse(null);

            if (kakaoUser != null) {
                TokenDto tokenDto = jwtTokenProvider.createToken(kakaoUser);
                kakaoUserInfo.setAccessToken(tokenDto.getAccessToken());
            }

            responseDto.setMsg(MessageConstants.SUCCESS_LOGIN);
            responseDto.setData(kakaoUserInfo);

        }catch(Exception e){
            logger.error("[KakaoUserService] : " + e,e);
        }

        return responseDto;
    }

    private String getAccessToken(String code) throws JsonProcessingException {

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", client_id);
        body.add("redirect_uri", redirect_uri);
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        return jsonNode.get("access_token").asText();
    }

    // 토큰으로 카카오 API 호출 // 닉네임관련 예외가 필요함.
    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        Long id = jsonNode.get("id").asLong();
        String username = "";
        if(jsonNode.get("properties") == null){
            username = jsonNode.get("id").asText();
        }else {
            username = jsonNode.get("properties").get("nickname").asText();
        }
        String email = jsonNode.get("kakao_account").get("email").asText();

        return new KakaoUserInfoDto(username,email,id,null);
    }

    private void registerKakaoUser(KakaoUserInfoDto kakaoUserInfo) {

        String password = UUID.randomUUID().toString();
        String encodedPassword = passwordEncoder.encode(password);

        User kakaoUser = new User(kakaoUserInfo.getUsername(),encodedPassword,kakaoUserInfo.getEmail(),kakaoUserInfo.getKakaoId());
        userRepository.save(kakaoUser);
    }

}
