package com.sideproject.sharingtimer.service.Impl;

import com.sideproject.sharingtimer.dto.RoomRequestDto;
import com.sideproject.sharingtimer.dto.RoomResponseDto;
import com.sideproject.sharingtimer.model.Room;
import com.sideproject.sharingtimer.model.User;
import com.sideproject.sharingtimer.repository.RoomRepository;
import com.sideproject.sharingtimer.security.UserDetailsImpl;
import com.sideproject.sharingtimer.service.RoomService;
import com.sideproject.sharingtimer.util.constants.MessageConstants;
import com.sideproject.sharingtimer.util.exception.CustomException;
import com.sideproject.sharingtimer.util.exception.ErrorCode;
import com.sideproject.sharingtimer.util.exception.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    private static final Logger logger = LoggerFactory.getLogger(RoomServiceImpl.class);


    @Override
    @Transactional
    public ResponseDto createRoom(UserDetailsImpl userDetails, RoomRequestDto requestDto) throws Exception {
        logger.info("[checkParam] : " + requestDto);

        ResponseDto responseDto = new ResponseDto();
        RoomResponseDto roomResponseDto = new RoomResponseDto();

        try {
            User user = userDetails.getUser();
            if (user == null) {
                //logger 필요
                logger.error("[NOT_FOUNT_USER_INFO]");
                responseDto.setErrorCode(new CustomException(ErrorCode.NOT_FOUND_USER_INFO));
                return responseDto;
            }
        /*
            방과 유저에 대한 예외 설정이 필요해보인다.
        */

            //방생성
            String roomId = UUID.randomUUID().toString();
            String roomName = requestDto.getRoomName();

            Room room = Room.builder()
                    .roomId(roomId)
                    .roonName(roomName)
                    .build();

            roomRepository.save(room);
            // 매핑
            roomResponseDto.setRoomId(room.getRoomId());
            roomResponseDto.setRoomName(room.getRoonName());

            responseDto.setData(roomResponseDto);
            responseDto.setMsg(MessageConstants.SUCCESS_CREATE_ROOM);

        }catch (Exception e){
            throw new CustomException(ErrorCode.FAIL_CREATE_ROOM);
        }

        return responseDto;
    }

    @Override
    @Transactional
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
