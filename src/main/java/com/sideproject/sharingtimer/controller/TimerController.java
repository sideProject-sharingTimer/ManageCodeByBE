package com.sideproject.sharingtimer.controller;


import com.sideproject.sharingtimer.dto.RoomRequestDto;
import com.sideproject.sharingtimer.security.UserDetailsImpl;
import com.sideproject.sharingtimer.service.RoomService;
import com.sideproject.sharingtimer.service.TimerService;
import com.sideproject.sharingtimer.util.exception.ResponseDto;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"시간"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/timer")
public class TimerController {

    private final TimerService timerService;

    @PostMapping("/start")
    @ApiOperation(value = "시작 시간" , notes = "타이머 시작 시간을 저장한다.")
    @ApiParam(value = "roomId" , example = "방의 고유번호" , required = true)
    public ResponseEntity<Object> recordStTime(@AuthenticationPrincipal UserDetailsImpl userDetails,@RequestParam String roomId) throws Exception{
        ResponseDto responseDto = timerService.recordStTime(userDetails,roomId);
        return new ResponseEntity<>(responseDto , HttpStatus.OK);
    }

    @PostMapping("/stop")
    @ApiOperation(value = "시간 정지" , notes = "타이머 정지시간을 저장한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "roomId", example = "방의 고유번호", required = true),
            @ApiImplicitParam(value = "asTime", example = "해당 유저의 누적된 시간", required = true)
    })
    public ResponseEntity<Object> recordEdTime(@AuthenticationPrincipal UserDetailsImpl userDetails,@RequestParam String roomId ,@RequestParam String asTime) throws Exception{
        ResponseDto responseDto = timerService.recordUtTime(userDetails,roomId,asTime);
        return new ResponseEntity<>(responseDto , HttpStatus.OK);
    }



}
