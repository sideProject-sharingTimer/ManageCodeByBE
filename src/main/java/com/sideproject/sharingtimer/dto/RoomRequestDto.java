package com.sideproject.sharingtimer.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor // 필요한 생성자 생성 ,
@NoArgsConstructor
@Data
public class RoomRequestDto {

    @ApiModelProperty(position = 1 ,value = "roomName",example ="방 제목",required = true )
    private String roomName;
    @ApiModelProperty(position = 2 ,value = "limitCnt",example ="인원수 제한",required = true )
    private int limitCnt;

}
