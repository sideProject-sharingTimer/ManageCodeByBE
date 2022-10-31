package com.sideproject.sharingtimer.service.Impl;

import com.google.gson.Gson;
import com.sideproject.sharingtimer.dto.RoomResponseDto;
import com.sideproject.sharingtimer.model.Room;
import com.sideproject.sharingtimer.model.User;
import com.sideproject.sharingtimer.repository.RoomRepository;
import com.sideproject.sharingtimer.security.UserDetailsImpl;
import com.sideproject.sharingtimer.service.RoomService;
import com.sideproject.sharingtimer.util.exception.CustomException;
import com.sideproject.sharingtimer.util.exception.ErrorCode;
import com.sideproject.sharingtimer.util.exception.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    Gson gson = new Gson();

    @Override
    @Transactional
    public ResponseDto createRoom(UserDetailsImpl userDetails) throws Exception {

        ResponseDto responseDto = new ResponseDto();
        RoomResponseDto roomResponseDto = new RoomResponseDto();

        try {
            User user = userDetails.getUser();
            if (user == null) {
                //logger 필요
                responseDto.setErrorCode(new CustomException(ErrorCode.NOT_FOUND_USER_INFO));
                return responseDto;
            }
        /*
            방과 유저에 대한 예외 설정이 필요해보인다.
        */

            //방생성
            String roomId = UUID.randomUUID().toString();

            Room room = Room.builder()
                    .roomId(roomId)
                    .build();

            roomRepository.save(room);
            // 매핑
            roomResponseDto.setRoomId(room.getRoomId());
            responseDto.setData(gson.toJson(roomResponseDto));

        }catch (Exception e){
            throw new CustomException(ErrorCode.FAIL_CREATE_ROOM);
        }

        return responseDto;
    }

    @Override
    public ResponseDto selectRoomList() throws Exception {
        ResponseDto responseDto = new ResponseDto();
        try {
            List<Room> selectRoomList = roomRepository.findAllByOrderByModifiedAt();
            responseDto.setData(selectRoomList);

        }catch (Exception e){
            throw new CustomException(ErrorCode.ERROR_SELECT_DATA);
        }
        return responseDto;
    }
}
