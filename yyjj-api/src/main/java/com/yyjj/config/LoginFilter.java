package com.yyjj.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//@WebFilter(filterName = "LoginFilter", urlPatterns = {"/*"})
//@Order(value = 1)
//@Configuration
public class LoginFilter implements Filter {
	 private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
	            Arrays.asList("/index.html","/user/login", "/user/logout", "/register")));
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
        
        boolean allowedPath = ALLOWED_PATHS.contains(path);
 
        if (allowedPath) {
          
        	filterChain.doFilter(servletRequest, servletResponse);
        }
        else {
          

  
        
//        // 响应标头指定 指定可以访问资源的URI路径
//        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
//
//        //响应标头指定响应访问所述资源到时允许的一种或多种方法
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
//
//        //设置 缓存可以生存的最大秒数
//        response.setHeader("Access-Control-Max-Age", "3600");
//
//        //设置  受支持请求标头
//        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
//
//        // 指示的请求的响应是否可以暴露于该页面。当true值返回时它可以被暴露
//        response.setHeader("Access-Control-Allow-Credentials","true");
        
        Object user = request.getSession().getAttribute("user");
        	if(Objects.nonNull(user)) {
        		filterChain.doFilter(servletRequest, servletResponse);
        	}else {
        		String type = request.getHeader("X-Requested-With");// XMLHttpRequest     HttpServletRequest -> request
        		if ("XMLHttpRequest".equals(type)) {
                //是ajax请求
                // 异步请求下的重定向
	                response.addHeader("FLAG", "-1");
	                response.setHeader("SESSIONSTATUS", "TIMEOUT");
	                response.setHeader("CONTEXTPATH", "http://192.168.1.222:8080/index.html");//重定向目标地址
                response.setStatus(1000);
            	} else {
            	//非ajax请求，直接使用重定向
            		response.sendRedirect("http://192.168.1.222:8080/index.html");
            	}
        	} 
        }
    }

    @Override
    public void destroy() {

    }
}
