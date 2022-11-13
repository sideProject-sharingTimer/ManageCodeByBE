package com.sideproject.sharingtimer.service;

import com.sideproject.sharingtimer.util.exception.ResponseDto;


public interface KakaoUserService {

    /*
     DESC : 카카오톡 로그인 테스트
   */
    ResponseDto kakaoLogin(String code) throws Exception;


}
