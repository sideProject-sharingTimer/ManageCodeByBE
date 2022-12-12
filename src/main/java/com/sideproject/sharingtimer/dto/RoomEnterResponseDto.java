package com.sideproject.sharingtimer.dto;

import com.sideproject.sharingtimer.model.Timer;
import com.sideproject.sharingtimer.model.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor // 필요한 생성자 생성 ,
@NoArgsConstructor
@Builder
@Getter
public class RoomEnterResponseDto {

    @ApiModelProperty(position = 1 ,value = "roomId",example ="방 고유번호",required = true )
    private String roomId;
    @ApiModelProperty(position = 2 ,value = "roomName",example ="방 이름",required = true )
    private String roomName;
    @ApiModelProperty(position = 3 ,value = "senderId",example ="해당 방의 시간 공유",required = true )
    private List<User> userTimerList;


}
