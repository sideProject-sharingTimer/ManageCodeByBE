package com.sideproject.sharingtimer.service.Impl;

import com.sideproject.sharingtimer.model.Timer;
import com.sideproject.sharingtimer.model.User;
import com.sideproject.sharingtimer.repository.TimerRepository;
import com.sideproject.sharingtimer.security.UserDetailsImpl;
import com.sideproject.sharingtimer.service.TimerService;
import com.sideproject.sharingtimer.util.exception.CustomException;
import com.sideproject.sharingtimer.util.exception.ErrorCode;
import com.sideproject.sharingtimer.util.exception.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TimerServiceImpl implements TimerService {

    private static final Logger logger = LoggerFactory.getLogger(TimerServiceImpl.class);

    private final TimerRepository timerRepository;
    /*
           진행 :
           1. 요청 파라미터로 방 번호 , 토큰 정보에서 유저정보를 받아온다.
           2. 최초 시작 시 , 시작과 끝 시간을 0 으로 셋팅
           3. 방 번호와 유저정보에 해당하는 시간들이 있다면 해당 시간을 현 시간으로 업데이트 한다.

     */
    @Override
    @Transactional
    public ResponseDto recordStTime(UserDetailsImpl userDetails, String roomId) throws Exception {
        ResponseDto responseDto = new ResponseDto();

        try {

            User user = userDetails.getUser();
            if (user == null) {
                //logger 필요
                logger.error("[NOT_FOUNT_USER_INFO]");
                responseDto.setErrorCode(new CustomException(ErrorCode.NOT_FOUND_USER_INFO));
                return responseDto;
            }
            // 이미 해당 방에서 시작한 타이머가 존재한다면 , 해당 타이머의 시작 시간을 변경해준다.
            Optional<Timer> timer = timerRepository.findByRoomIdAndUserId(roomId, user.getId());
            if (timer.isPresent()){
                LocalDateTime now = LocalDateTime.now();
                String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd, HH-mm-ss"));
                timer.get().updateStTime(formatedNow);

            }else {
                Timer createTimer = Timer.builder()
                        .roomId(roomId)
                        .userId(user.getId())
                        .build();
                timerRepository.save(createTimer);
            }


        }catch (Exception e){
            logger.error("[TimerService] timerStart : " + e,e);
            throw new CustomException(ErrorCode.FAIL_RECORD_ST_TIME);
        }
        return responseDto;
    }

    @Override
    @Transactional
    public ResponseDto recordEdTime(UserDetailsImpl userDetails, String roomId) throws Exception {
        ResponseDto responseDto = new ResponseDto();
        try {

            User user = userDetails.getUser();
            if (user == null) {
                //logger 필요
                logger.error("[NOT_FOUNT_USER_INFO]");
                responseDto.setErrorCode(new CustomException(ErrorCode.NOT_FOUND_USER_INFO));
                return responseDto;
            }

            // 이미 해당 방에서 시작한 타이머가 존재한다면 , 해당 타이머의 시작 시간을 변경해준다.
            Optional<Timer> timer = timerRepository.findByRoomIdAndUserId(roomId, user.getId());
            if (timer.isPresent()){
                LocalDateTime now = LocalDateTime.now();
                String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd, HH-mm-ss"));
                timer.get().updateEdTime(formatedNow);

            }


        }catch (Exception e){
            logger.error("[TimerService] timerStop : " + e,e);
            throw new CustomException(ErrorCode.FAIL_RECORD_ED_TIME);
        }




        return responseDto;
    }
}
