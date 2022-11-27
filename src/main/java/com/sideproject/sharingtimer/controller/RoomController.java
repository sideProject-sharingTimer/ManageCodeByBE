package com.sideproject.sharingtimer.controller;

import com.sideproject.sharingtimer.dto.RoomRequestDto;
import com.sideproject.sharingtimer.security.UserDetailsImpl;
import com.sideproject.sharingtimer.service.RoomService;
import com.sideproject.sharingtimer.util.exception.ResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"방"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/create")
    @ApiOperation(value = "방 생성" , notes = "시간공유를 위한 방을 생성한다.")
    public ResponseEntity<Object> roomCreate(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody RoomRequestDto requestDto) throws Exception{
        ResponseDto responseDto = roomService.createRoom(userDetails, requestDto);
        return new ResponseEntity<>(responseDto , HttpStatus.OK);
    }

    @GetMapping("/list")
    @ApiOperation(value = "방 목록 조회" , notes = "생성된 방의 목록을 조회한다.")
    public ResponseEntity<Object> selectRoomList() throws Exception{
        ResponseDto responseDto = roomService.selectRoomList();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/enter")
    @ApiOperation(value = "방 입장" , notes = "선택한 방에 입장한다.")
    @ApiParam(value = "roomId" , example = "방의 고유번호" , required = true)
    public ResponseEntity<Object> enterRoom(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam String roomId) throws Exception{
        ResponseDto responseDto = roomService.enterRoom(userDetails,roomId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }


}
