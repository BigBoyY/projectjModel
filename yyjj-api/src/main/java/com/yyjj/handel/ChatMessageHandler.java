package com.yyjj.handel;

import java.io.IOException;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.yyjj.config.WsSessionManage;



public class ChatMessageHandler extends TextWebSocketHandler{

	private static final Logger log = LoggerFactory.getLogger(ChatMessageHandler.class);
	 /**
     * socket 建立成功事件
     * 连接成功时候，会触发UI上onopen方法 
     * @param session
     * @throws Exception
     */
    @Override  
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {  
        //System.out.println("connect to the websocket success......");  
       // SecurityContext context = SecurityContextHolder.getContext();	    
       // Authentication authen = context.getAuthentication();
      //  String name = authen.getName();
        Object userId =  session.getAttributes().get("userId");
        log.info("socket用户id"+userId);
        
        WsSessionManage.add(userId, session);
        
        //users.put(++count, session);
         //users.add(session);  
        // 这块会实现自己业务，比如，当用户登录后，会把离线消息推送给用户  
        // TextMessage returnMessage = new TextMessage("你将收到的离线");  
        // session.sendMessage(returnMessage);  
    }  
    /**
     * 初始化
     */
    static  {
    	//log.info("websokect初始化");
    	//users = new ConcurrentHashMap<Integer, WebSocketSession>();   	
    }
    
    /**
     * 接收消息事件
     *在UI在用js调用websocket.send()时候，会调用该方法 
     * @param session
     * @param message
     * @throws Exception
     */
    @Override  
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {  
         sendMessageToUsers(message);  
        //super.handleTextMessage(session, message);  
    }  
  
    /** 
     * 给某个用户发送消息 
     * 
     * @param userName 
     * @param message 
     */  
    public void sendMessageToUser(Integer userName, TextMessage message) {
    	try {
    		WsSessionManage.get(userName.toString()).sendMessage(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       // for (WebSocketSession user : users.) {  
          //  if (user.getAttributes().get(Constants.WEBSOCKET_USERNAME).equals(userName)) {  
           //     try {  
               //     if (user.isOpen()) {  
              //          user.sendMessage(message);  
              //      }  
              //  } catch (IOException e) {  
              //      e.printStackTrace();  
             //   }  
             //   break;  
           // }  
        //}  
    }  
  
    /** 
     * 给所有在线用户发送消息 
     * 
     * @param message 
     */  
    public static void sendMessageToUsers(TextMessage message) {  
    	
        for (Entry<Object, WebSocketSession> user : WsSessionManage.getWsSessions().entrySet()) {
        	WebSocketSession session = user.getValue();
            try {  
                if (session.isOpen()) {  
                	log.info("向客户端"+session.getId()+"发送消息:"+message);
                	session.sendMessage(message);  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }
    
	@Override  
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {  
        if (session.isOpen()) {  
            session.close();  
        }  
        log.debug("websocket connection closed......");  
        WsSessionManage.remove(session);  
    }  
    
    /**
     * socket 断开连接时
     *
     * @param session
     * @param status
     * @throws Exception
     */
	@Override  
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {  
    	log.debug("websocket connection closed......");  
    	WsSessionManage.remove(session);  
    }  
  
    @Override  
    public boolean supportsPartialMessages() {  
        return false;  
    }  
}
