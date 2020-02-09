package com.yyjj.handel;

import java.util.Map;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

public class ChatHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception ex) {
			System.out.println("After Handshake"); 
		super.afterHandshake(request, response, wsHandler, ex);
	}
	
	
	
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		System.out.println("before Handshake"); 
		Object user =  SecurityUtils.getSubject().getSession().getAttribute("user");  
        if (user == null) {  
        	attributes.put("userId", UUID.randomUUID().toString().replaceAll("-","")); 
        }  else {
        	attributes.put("userId",user);
        }
         
  
       // return super.beforeHandshake(request, response, wsHandler, attributes); 
		return super.beforeHandshake(request, response, wsHandler, attributes);
	}
}
