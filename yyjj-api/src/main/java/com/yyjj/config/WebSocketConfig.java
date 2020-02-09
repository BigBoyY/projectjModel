package com.yyjj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.yyjj.handel.ChatHandshakeInterceptor;
import com.yyjj.handel.ChatMessageHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) { 
		registry.addHandler(chatMessageHandler()
				 ,"/webSocket")
		 .addInterceptors(new ChatHandshakeInterceptor()).setAllowedOrigins("*");
		 
		
	     registry.addHandler(
	    		 chatMessageHandler()
	    		 , "/sockjs/webSocketServer")
	     .addInterceptors(new ChatHandshakeInterceptor()).withSockJS();  
	}
	

    @Bean  
    public TextWebSocketHandler chatMessageHandler(){  
        return new ChatMessageHandler();  
    } 

}
