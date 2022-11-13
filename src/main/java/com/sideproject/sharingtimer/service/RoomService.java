package com.sideproject.sharingtimer.service;

import com.sideproject.sharingtimer.dto.RoomRequestDto;
import com.sideproject.sharingtimer.security.UserDetailsImpl;
import com.sideproject.sharingtimer.util.exception.ResponseDto;

public interface RoomService {

    /*
     DESC : 방 생성
   */
    ResponseDto createRoom(UserDetailsImpl userDetails, RoomRequestDto requestDto)throws Exception;
    /*
     DESC : 방 목록 조회
   */
    ResponseDto selectRoomList() throws Exception;

}
