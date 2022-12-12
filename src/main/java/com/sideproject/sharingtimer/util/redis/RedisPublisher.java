package com.sideproject.sharingtimer.util.redis;

import com.sideproject.sharingtimer.dto.RoomEnterResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisPublisher {

    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(String roomId, RoomEnterResponseDto roomEnterResponseDto){
        redisTemplate.convertAndSend(roomId , roomEnterResponseDto);
    }
}