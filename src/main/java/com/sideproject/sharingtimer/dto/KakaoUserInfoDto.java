package com.sideproject.sharingtimer.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@AllArgsConstructor // 필요한 생성자 생성 ,
@Getter
@Setter
public class KakaoUserInfoDto {

    @ApiModelProperty(position = 1 ,value = "username",example ="홍길동",required = true )
    private String username;
    @ApiModelProperty(position = 2 ,value = "email",example ="test1234@naver.com",required = true )
    private String email;
    @ApiModelProperty(position = 3 ,value = "kakaoId",example ="kakaoId",required = true )
    private Long kakaoId;
    @ApiModelProperty(position = 4 ,value = "accessToken",example ="token",required = true )
    private String accessToken;
}
