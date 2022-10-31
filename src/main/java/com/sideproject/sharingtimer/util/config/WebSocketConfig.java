package com.sideproject.sharingtimer.util.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;


@Slf4j
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/ws-stomp") // 핸드쉐이크를 통해 커네션을 갖는다.
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        /*
            sub : (/sub/timer/room/ + {roomId})
         */
        registry.enableSimpleBroker("/sub");

        /*
            pub : (/pub/timer/time)
         */
        registry.setApplicationDestinationPrefixes("/pub");

    }

}
