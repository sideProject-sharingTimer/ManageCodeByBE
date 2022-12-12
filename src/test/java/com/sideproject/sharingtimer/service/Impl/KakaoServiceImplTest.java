package com.sideproject.sharingtimer.service.Impl;

import com.sideproject.sharingtimer.model.Timer;
import org.hibernate.collection.internal.PersistentList;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class KakaoServiceImplTest {

    private static final Logger logger = LoggerFactory.getLogger(KakaoServiceImplTest.class);

    @Test
    void getAuthCode() {

        //Spring restTemplate
        HashMap<String, Object> result = new HashMap<>();
        ResponseEntity<Object> resultMap = new ResponseEntity<>(null,null,200);

        try {

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders header = new HttpHeaders();
            HttpEntity<?> entity = new HttpEntity<>(header);
            String url = "https://kauth.kakao.com/oauth/authorize?client_id=047ec2a70edbb613094683ba332ce8fa&redirect_uri=http://43.201.95.19:8080/user/kakao/login&response_type=code";
//https://kauth.kakao.com/oauth/authorize?client_id=047ec2a70edbb613094683ba332ce8fa&redirect_uri=http://localhost:8080/user/kakao/login&response_type=code
            UriComponents uri = UriComponentsBuilder.fromHttpUrl(url).build();

            resultMap = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Object.class);

            result.put("statusCode", resultMap.getStatusCodeValue()); //http status code를 확인
            result.put("header", resultMap.getHeaders()); //헤더 정보 확인
            result.put("body", resultMap.getBody()); //실제 데이터 정보 확인

            //에러처리해야댐
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            result.put("statusCode", e.getRawStatusCode());
            result.put("body"  , e.getStatusText());
            logger.error("[getAuthCode] " + e,e);
        }
        catch (Exception e) {
            result.put("statusCode", "999");
            result.put("body"  , "excpetion오류");
            logger.error("[getAuthCode] " + e,e);

        }
        System.out.println(result);

    }

    @Test
    void kakaoLogin() {
        System.out.println("hello");
    }

    @Test
    void deleteTest(){

        List<Timer> timerList = new ArrayList<>();
        Timer timer1 = new Timer();
        timerList.add(timer1);
        Timer timer2 = new Timer();
        timerList.add(timer2);
        System.out.println(timerList);


    }


}