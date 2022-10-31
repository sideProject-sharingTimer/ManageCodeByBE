package com.sideproject.sharingtimer.dto;

import lombok.*;

@AllArgsConstructor // 필요한 생성자 생성 ,
@Getter
@Setter
public class KakaoUserInfoDto {

    private String username;

    private String email;

    private Long kakaoId;

    private String accessToken;
}
