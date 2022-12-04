package com.sideproject.sharingtimer.service.Impl;

import com.sideproject.sharingtimer.model.Timer;
import com.sideproject.sharingtimer.model.User;
import com.sideproject.sharingtimer.repository.TimerRepository;
import com.sideproject.sharingtimer.security.UserDetailsImpl;
import com.sideproject.sharingtimer.service.TimerService;
import com.sideproject.sharingtimer.util.constants.MessageConstants;
import com.sideproject.sharingtimer.util.constants.StringConstants;
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
            // 어떤 방에서 어떤 유저가 시간을 변경.
            Optional<Timer> timer = timerRepository.findByRoomIdAndUserId(roomId, user.getId());
            if (timer.isPresent()){
                LocalDateTime now = LocalDateTime.now();
                String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd, HH-mm-ss"));
                timer.get().recordStTime(formatedNow);
                timer.get().updateStatus(StringConstants.TIMER_START);
            }else {
                throw new CustomException(ErrorCode.NOT_FOUND_TIMER_BY_USER);
            }

            responseDto.setMsg(MessageConstants.SUCCESS_SAVE_TIMER_BY_USER);
        }catch (Exception e){
            logger.error("[TimerService] recordStTime : " + e,e);
            throw new CustomException(ErrorCode.FAIL_RECORD_ST_TIME);
        }
        return responseDto;
    }

    @Override
    @Transactional
    public ResponseDto recordUtTime(UserDetailsImpl userDetails, String roomId, String asTime) throws Exception {
        ResponseDto responseDto = new ResponseDto();
        try {

            User user = userDetails.getUser();
            if (user == null) {
                //logger 필요
                logger.error("[NOT_FOUNT_USER_INFO]");
                responseDto.setErrorCode(new CustomException(ErrorCode.NOT_FOUND_USER_INFO));
                return responseDto;
            }

            // 시간 동작 상태의 변화를 요청한 유저의 시간을 찾는다.
            Optional<Timer> timer = timerRepository.findByRoomIdAndUserId(roomId, user.getId());
            if (timer.isPresent()){
                LocalDateTime now = LocalDateTime.now();
                String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd, HH-mm-ss"));
                timer.get().updateUtTime(formatedNow);
                timer.get().updateStatus(StringConstants.TIMER_STOP);
                timer.get().updateAsTime(asTime);
            }else {
                throw new CustomException(ErrorCode.NOT_FOUND_TIMER_BY_USER);
            }

            responseDto.setMsg(MessageConstants.SUCCESS_SAVE_TIMER_BY_USER);
        }catch (Exception e){
            logger.error("[TimerService] recordUtTime : " + e,e);
            throw new CustomException(ErrorCode.FAIL_RECORD_ED_TIME);
        }

        return responseDto;
    }
}
