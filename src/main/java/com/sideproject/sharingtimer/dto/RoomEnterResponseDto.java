package com.sideproject.sharingtimer.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor // 필요한 생성자 생성 ,
@NoArgsConstructor
@Builder
public class RoomEnterResponseDto {

    @ApiModelProperty(position = 1 ,value = "roomId",example ="방 고유번호",required = true )
    private String roomId;
}
