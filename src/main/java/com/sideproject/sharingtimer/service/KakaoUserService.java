package com.sideproject.sharingtimer.service;

import com.sideproject.sharingtimer.dto.KakaoUserInfoDto;

public interface KakaoUserService {

    KakaoUserInfoDto kakaoLogin(String code) throws Exception;


}
