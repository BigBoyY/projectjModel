/*
 * *
 *  * blog.coder4j.cn
 *  * Copyright (C) 2016-2019 All Rights Reserved.
 *
 */
package com.yyjj.config;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.WebSocketSession;


/**
 * @author buhao
 * @version WsSessionManager.java, v 0.1 2019-10-22 10:24 buhao
 */
public class WsSessionManage {
    /**
     * 保存连接 session 的地方
     */
    private static ConcurrentHashMap<Object, WebSocketSession> SESSION_POOL = new ConcurrentHashMap<>();

    /**
     * 添加 session
     *
     * @param key
     */
    public static void add(Object key, WebSocketSession session) {
        // 添加 session
        SESSION_POOL.put(key, session);
    }
    
    /**
     * 获取map容器
     * @return
     */
    public static Map<Object, WebSocketSession> getWsSessions(){
    	return SESSION_POOL;
    }
    
    /**
     * 删除 session,会返回删除的 session
     *
     * @param key
     * @return
     */

	public static WebSocketSession remove(WebSocketSession key) {
        // 删除 session
        return SESSION_POOL.remove(key);
    }

    /**
     * 删除并同步关闭连接
     *
     * @param key
     */
    public static void removeAndClose(WebSocketSession key) {
        WebSocketSession session = remove(key);
        if (session != null) {
            try {
                // 关闭连接
                session.close();
            } catch (IOException e) {
                // todo: 关闭出现异常处理
                e.printStackTrace();
            }
        }
    }

    /**
     * 获得 session
     *
     * @param key
     * @return
     */
    public static WebSocketSession get(String key) {
        // 获得 session
        return SESSION_POOL.get(key);
    }
}