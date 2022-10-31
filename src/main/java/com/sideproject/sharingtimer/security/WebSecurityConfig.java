package com.sideproject.sharingtimer.security;

import com.sideproject.sharingtimer.security.jwt.JwtAuthenticationFilter;
import com.sideproject.sharingtimer.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtTokenProvider jwtTokenProvider;

  @Bean
  public BCryptPasswordEncoder encodePassword() {
    return new BCryptPasswordEncoder();
  }

  @Override
  public void configure(WebSecurity web) {
    // h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
    web
            .ignoring()
            .antMatchers("/h2-console/**")
            .antMatchers("/webSocket/**")
            .antMatchers(AUTH_WHITELIST);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.cors().configurationSource(corsConfigurationSource());
    http.csrf().disable().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.headers().frameOptions().disable();
    http.authorizeRequests();
    // 회원 관리 처리 API (POST /user/**) 에 대해 CSRF 무시

    http.authorizeRequests()
            // login 없이 허용
            .antMatchers("/user/**").permitAll()
            // webSocket 요청 허용
            .antMatchers("/webSocket/**").permitAll()
            //추가 - 메인 페이지 접근 허용
            .antMatchers("/").permitAll()

        // 그 외 어떤 요청이든 '인증'과정 필요
        .anyRequest().authenticated()
        .and()
        .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
            UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowCredentials(true) ;
    configuration.addAllowedOriginPattern("*");
    configuration.addAllowedOrigin("http://localhost:3000"); // local 테스트 시 , 해당 포트 접근 허용
    configuration.addAllowedOrigin("http://localhost:8080");
    configuration.addAllowedOrigin("http://43.201.95.19:8080");
    configuration.addAllowedMethod("*");
    configuration.addAllowedHeader("*");
    configuration.addExposedHeader("Authorization");

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  private static final String[] AUTH_WHITELIST = {
          // -- Static resources
          "/css/**",
          "/images/**",
          "/js/**",
          // -- Swagger UI v2
          "/v2/api-docs",
          "/configuration/ui",
          "/swagger-resources/**",
          "/configuration/security",
          "/swagger-ui.html",
          "/webjars/**",
          // -- Swagger UI v3 (Open API)
          "/v3/api-docs/**",
          "/swagger-ui/**"
  };
}