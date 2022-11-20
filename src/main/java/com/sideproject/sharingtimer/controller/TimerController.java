package com.sideproject.sharingtimer.controller;


import com.sideproject.sharingtimer.dto.RoomRequestDto;
import com.sideproject.sharingtimer.security.UserDetailsImpl;
import com.sideproject.sharingtimer.util.exception.ResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"시간"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/timer")
public class TimerController {


    @PostMapping("/start")
    @ApiOperation(value = "시작 시간" , notes = "타이머 시작 시간을 저장한다.")
    public ResponseEntity<Object> timerStart(@AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception{
        ResponseDto responseDto = new ResponseDto();
        return new ResponseEntity<>(responseDto , HttpStatus.OK);
    }




}
