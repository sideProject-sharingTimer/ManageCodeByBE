package com.sideproject.sharingtimer.security.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenRequestDto {
  private String accessToken;
  private Long userId;
}