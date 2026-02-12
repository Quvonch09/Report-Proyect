package sfera.reportproyect.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{ @Override
public void configureMessageBroker(MessageBrokerRegistry registry) {
    // Client SUBSCRIBE qiladi: /topic/...
    registry.enableSimpleBroker("/topic", "/queue");

    // Client SEND qiladi: /app/...
    registry.setApplicationDestinationPrefixes("/app");

    // (ixtiyoriy) userga private xabarlar uchun
    registry.setUserDestinationPrefix("/user");
}

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Frontend shu endpointga ulanadi
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS(); // SockJS bo'lmasa olib tashlasa ham bo'ladi
    }
}
