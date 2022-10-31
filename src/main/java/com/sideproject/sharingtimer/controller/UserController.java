package com.sideproject.sharingtimer.controller;


import com.sideproject.sharingtimer.dto.KakaoUserInfoDto;
import com.sideproject.sharingtimer.service.KakaoUserService;
import com.sideproject.sharingtimer.util.constants.MessageConstants;
import com.sideproject.sharingtimer.util.exception.ResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"SNS LOGIN API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final KakaoUserService kakaoUserService;

    //카카오 로그인 API 테스트
    @PostMapping("/kakao/login")
    @ApiOperation(value = "카카오 로그인" , notes = "카카오톡 로그인 테스트 , 발급받은 인가코드를 파라미터로 전달받는다.")
    @ApiParam(value = "code" , example = "발급받은 인가코드" , required = true)
    public ResponseEntity<Object> kakaoLogin(@RequestParam String code) throws Exception {
        // 캐스팅 수정 필요
        KakaoUserInfoDto kakaoUserInfoDto = kakaoUserService.kakaoLogin(code);
        return new ResponseEntity<>(new ResponseDto(MessageConstants.SUCCESS_LOGIN , kakaoUserInfoDto), HttpStatus.OK);
    }
}
