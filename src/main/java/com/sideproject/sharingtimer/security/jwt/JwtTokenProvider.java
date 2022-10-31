package com.sideproject.sharingtimer.security.jwt;

import com.sideproject.sharingtimer.model.User;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

  @Value("${jwt.secret-key}")
  private String secretKey;

  // Access Token 유효기간 - 하루
  private static final Long accessTokenValidTime = 24 * 60 * 60 * 1000L;

  // Refresh Token 유효기간 - 7일
  private static final Long refreshTokenValidTime = 7 * 24 * 60 * 60 * 1000L;

  private final UserDetailsService userDetailsService;

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  // 토큰 생성
  public TokenDto createToken(User user) {

    Map<String, Object> headers = new HashMap<>();
    headers.put("typ", "JWT");

    Claims claims = Jwts.claims().setSubject(String.valueOf(user.getId()));
    claims.put("kakaoId", user.getKakaoId());
    claims.put("username", user.getUsername());
    claims.put("email", user.getEmail());

    Date now = new Date();

    String accessToken = Jwts.builder()
        .setHeader(headers)
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(new Date(now.getTime() + accessTokenValidTime))
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();

    return TokenDto.builder()
        .accessToken(accessToken)
        .accessTokenExpiresIn(accessTokenValidTime)
        .build();
  }

  // 토큰에서 회원 정보 추출
  public String getUserPk(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
  }

  // JWT 토큰에서 인증 정보 조회
  public Authentication getAuthentication(String token) {
    UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String resolveToken(HttpServletRequest request) {
    return request.getHeader("Authorization");
  }

  // 토큰의 유효성 + 만료일자 확인
  public JwtReturn validateToken(String jwtToken) {

    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
      if (claims.getBody().getExpiration().after(new Date())) {
        return JwtReturn.SUCCESS;
      } else {
        return JwtReturn.FAIL;
      }
    } catch (ExpiredJwtException e) {
      log.info("만료된 JWT 토큰입니다");
      return JwtReturn.EXPIRED;
    }
  }
}