package com.sideproject.sharingtimer.dto;

import com.sideproject.sharingtimer.model.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.util.List;

@AllArgsConstructor // 필요한 생성자 생성 ,
@NoArgsConstructor
@Builder
public class RoomResponseDto {

    @ApiModelProperty(position = 1 ,value = "roomId",example ="방 고유번호",required = true )
    private String roomId;
    @ApiModelProperty(position = 2 ,value = "roomName",example ="방 제목",required = true )
    private String roomName;
    @ApiModelProperty(position = 3 ,value = "limitCnt",example ="방 인원제한 수",required = true )
    private int limitCnt;
    @ApiModelProperty(position = 4 ,value = "limitCnt",example ="방 참여 인원",required = true )
    private List<User> userList;
}
