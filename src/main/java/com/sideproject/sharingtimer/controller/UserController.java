package com.sideproject.sharingtimer.controller;


import com.sideproject.sharingtimer.dto.KakaoUserInfoDto;
import com.sideproject.sharingtimer.service.KakaoUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final KakaoUserService kakaoUserService;

    //카카오 로그인 API 테스트
    @PostMapping("/kakao/login")
    public ResponseEntity<Object> kakaoLogin(@RequestParam String code) throws Exception {
        KakaoUserInfoDto kakaoUserInfoDto = kakaoUserService.kakaoLogin(code);
        return new ResponseEntity<>(kakaoUserInfoDto,HttpStatus.OK);
    }
}
