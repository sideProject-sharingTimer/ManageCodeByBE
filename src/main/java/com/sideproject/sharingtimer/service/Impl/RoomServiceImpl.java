package com.sideproject.sharingtimer.service.Impl;

import com.sideproject.sharingtimer.dto.RoomEnterResponseDto;
import com.sideproject.sharingtimer.dto.RoomRequestDto;
import com.sideproject.sharingtimer.dto.RoomResponseDto;
import com.sideproject.sharingtimer.model.Room;
import com.sideproject.sharingtimer.model.Timer;
import com.sideproject.sharingtimer.model.User;
import com.sideproject.sharingtimer.repository.RoomRepository;
import com.sideproject.sharingtimer.security.UserDetailsImpl;
import com.sideproject.sharingtimer.service.RoomService;
import com.sideproject.sharingtimer.util.constants.MessageConstants;
import com.sideproject.sharingtimer.util.constants.StringConstants;
import com.sideproject.sharingtimer.util.exception.CustomException;
import com.sideproject.sharingtimer.util.exception.ErrorCode;
import com.sideproject.sharingtimer.util.exception.ResponseDto;
import com.sideproject.sharingtimer.util.redis.RedisPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RedisPublisher redisPublisher;

    private static final Logger logger = LoggerFactory.getLogger(RoomServiceImpl.class);


    @Override
    @Transactional
    public ResponseDto createRoom(UserDetailsImpl userDetails, RoomRequestDto requestDto) throws Exception {
        logger.info("[checkParam] : " + requestDto);

        ResponseDto responseDto = new ResponseDto();
        List<User> userList = new ArrayList<>();

        try {
            User user = userDetails.getUser();
            if (user == null) {
                //logger 필요
                logger.error("[NOT_FOUNT_USER_INFO]");
                responseDto.setErrorCode(new CustomException(ErrorCode.NOT_FOUND_USER_INFO));
                return responseDto;
            }
            //방 생성과 함께 유저목록을 만들어주며 최초 유저 등록 방식
            userList.add(user);

        /*
            방과 유저에 대한 예외 설정이 필요해보인다.
        */
            //방생성
            String roomId = UUID.randomUUID().toString();
            String roomName = requestDto.getRoomName();
            int limitCnt = requestDto.getLimitCnt();

            Room room = Room.builder()
                    .roomId(roomId)
                    .roomName(roomName)
                    .limitCnt(limitCnt)
                    .userList(userList)
                    .build();

            roomRepository.save(room);
            // 매핑
            RoomResponseDto roomResponseDto = RoomResponseDto.builder()
                    .roomId(roomId)
                    .roomName(roomName)
                    .limitCnt(limitCnt)
                    .userList(userList)
                    .build();

            responseDto.setData(roomResponseDto);
            responseDto.setMsg(MessageConstants.SUCCESS_CREATE_ROOM);

        }catch (Exception e){
            logger.error("[RoomService] createRoom : " + e,e);
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
            logger.error("[RoomService] selectRoomList : " + e,e);
            throw new CustomException(ErrorCode.ERROR_SELECT_DATA);
        }
        return responseDto;
    }

    @Override
    @Transactional
    public ResponseDto enterRoom(UserDetailsImpl userDetails, String roomId) throws Exception {
        ResponseDto responseDto = new ResponseDto();
        RoomEnterResponseDto roomEnterResponseDto = new RoomEnterResponseDto();

        try {
            //입장하는 유저
            User user = userDetails.getUser();

            if (user == null) {
                //logger 필요
                logger.error("[NOT_FOUNT_USER_INFO]");
                responseDto.setErrorCode(new CustomException(ErrorCode.NOT_FOUND_USER_INFO));
                return responseDto;
            }
            //해당유저에게 최초 시간설정을 한다.
            Timer timer = Timer.builder()
                    .userId(user.getId())
                    .roomId(roomId)
                    .stTime(StringConstants.TIMER_INIT)
                    .utTime(StringConstants.TIMER_INIT)
                    .status(StringConstants.TIMER_STOP)
                    .build();

            user.updateTimerList(timer);

            // 유저가 입장하려는 방에 대한 정보를 가져온다.
            Room room = roomRepository.findByRoomId(roomId).orElseThrow(
                    () -> new CustomException(ErrorCode.NOT_EXIST_ROOM)
            );
            // 해당 방의 유저 정보에 입장하는 유저의 정보 추가 후 업데이트
            room.updateUserList(user);
            // 인원수 증가 후 , 유저 리스트의 사이즈가 제한 수를 넘는다면 예외 처리
            room.checkLimitCnt(room.getUserList().size());
            // 방 -> 유저 정보 -> 유저정보 (시간)
            roomEnterResponseDto = RoomEnterResponseDto.builder()
                    .roomId(roomId)
                    .roomName(room.getRoomName())
                    .userTimerList(room.getUserList())
                    .build();


            responseDto.setMsg(MessageConstants.SUCCESS_ENTER_ROOM);

        }catch (Exception e){
            logger.error("[RoomService] enterRoom : " + e,e);
            throw new CustomException(ErrorCode.FAIL_ENTER_ROOM);
        }

        redisPublisher.publish(roomId , roomEnterResponseDto);
        return responseDto;
    }
}
