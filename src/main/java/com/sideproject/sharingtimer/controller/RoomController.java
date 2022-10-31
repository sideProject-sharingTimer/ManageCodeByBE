package com.sideproject.sharingtimer.controller;


import com.sideproject.sharingtimer.security.UserDetailsImpl;
import com.sideproject.sharingtimer.service.RoomService;
import com.sideproject.sharingtimer.util.exception.ResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/create")
    @ApiOperation(value = "방생성" , notes = "시간공유를 위한 방을 생성한다.")
    public ResponseEntity<Object> roomCreate(@AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception{
        ResponseDto responseDto = roomService.createRoom(userDetails);
        return new ResponseEntity<>(responseDto , HttpStatus.OK);
    }

    @GetMapping("/list")
    @ApiOperation(value = "방 목록 조회" , notes = "생성된 방의 목록을 조회한다.")
    public ResponseEntity<Object> selectRoomList(@AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception{
        ResponseDto responseDto = roomService.selectRoomList();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }



}
