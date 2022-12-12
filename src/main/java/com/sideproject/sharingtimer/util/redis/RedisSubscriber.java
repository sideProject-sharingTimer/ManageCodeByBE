package com.sideproject.sharingtimer.util.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sideproject.sharingtimer.dto.RoomEnterResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    /**
     * Redis에서 메세지가 발행(publish)되면 대기하고 있던 onMessage가 해당 메세지를 받아 처리
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try{
            // redis에서 발행된 데이터를 받아 deserialize
            String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
            // ChatMessage 객체로 매핑
            RoomEnterResponseDto roomMessage = objectMapper.readValue(publishMessage, RoomEnterResponseDto.class);
            // WebSocket 구독자에게 채팅 메세지 Send
            messagingTemplate.convertAndSend("/sub/share/time/" + roomMessage.getRoomId(), roomMessage);
        } catch (Exception e){
            log.error(e.getMessage());
        }
    }
}