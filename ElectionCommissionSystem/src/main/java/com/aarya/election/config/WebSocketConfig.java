package com.aarya.election.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // This is the endpoint your HTML will connect to
        registry.addEndpoint("/liveResults").setAllowedOriginPatterns("*").withSockJS();
    }
    
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // This is the "channel" your HTML will subscribe to
        registry.enableSimpleBroker("/topic");
        // This is the prefix for messages sent from the client to the server
        registry.setApplicationDestinationPrefixes("/app");
    }
}