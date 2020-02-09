package com.yyjj.context;

import javax.servlet.http.HttpServletRequest;

public class RequestHolder {
	 private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<>();
	    
	    public static HttpServletRequest getRequest() {
	    	HttpServletRequest request = requestHolder.get();
	    	requestHolder.remove();
	        return request;
	    }
	    
	    public static void setRequest(HttpServletRequest request) {
	    	requestHolder.set(request);
	    }

}
