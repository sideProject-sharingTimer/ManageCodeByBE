package com.sideproject.sharingtimer.controller;

import com.sideproject.sharingtimer.service.KakaoUserService;
import com.sideproject.sharingtimer.util.exception.ResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"로그인"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final KakaoUserService kakaoUserService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    //카카오 로그인 API 테스트
    @PostMapping("/kakao/login")
    @ApiOperation(value = "카카오 로그인" , notes = "카카오톡 로그인 테스트 , 발급받은 인가코드를 파라미터로 전달받는다.")
    @ApiParam(value = "code" , example = "발급받은 인가코드" , required = true)
    public ResponseEntity<Object> kakaoLogin(@RequestParam String code) throws Exception {
        logger.info("[kakaoLogin] checkParam:" + code );
        ResponseDto responseDto = kakaoUserService.kakaoLogin(code);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
