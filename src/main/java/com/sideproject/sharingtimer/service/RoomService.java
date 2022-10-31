package com.sideproject.sharingtimer.service;

import com.sideproject.sharingtimer.security.UserDetailsImpl;
import com.sideproject.sharingtimer.util.exception.ResponseDto;

public interface RoomService {

    ResponseDto createRoom(UserDetailsImpl userDetails)throws Exception;
    ResponseDto selectRoomList() throws Exception;

}
