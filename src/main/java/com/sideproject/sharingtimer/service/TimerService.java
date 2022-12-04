package com.sideproject.sharingtimer.service;

import com.sideproject.sharingtimer.security.UserDetailsImpl;
import com.sideproject.sharingtimer.util.exception.ResponseDto;

public interface TimerService {

    /*
     DESC : 시작 시간 저장
   */
    ResponseDto recordStTime(UserDetailsImpl userDetails, String roomId) throws Exception;

    /*
     DESC : 업데이트 (정지 및 재시작) 시간 저장 , 누적시간
   */
    ResponseDto recordUtTime(UserDetailsImpl userDetails, String roomId, String asTime) throws Exception;
}
