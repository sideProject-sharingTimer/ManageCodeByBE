package com.sideproject.sharingtimer.service;

import com.sideproject.sharingtimer.security.UserDetailsImpl;
import com.sideproject.sharingtimer.util.exception.ResponseDto;

public interface TimerService {

    /*
     DESC : 시작 시간 저장
   */
    ResponseDto recordStTime(UserDetailsImpl userDetails, String roomId) throws Exception;

    /*
     DESC : 정지 시간 저장
   */
    ResponseDto recordEdTime(UserDetailsImpl userDetails, String roomId) throws Exception;
}
