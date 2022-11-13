package com.sideproject.sharingtimer.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor // 필요한 생성자 생성 ,
@NoArgsConstructor
@Data
public class RoomRequestDto {

    private String roomName;

}
