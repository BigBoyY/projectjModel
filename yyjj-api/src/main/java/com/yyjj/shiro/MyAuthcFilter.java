package com.yyjj.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

public class MyAuthcFilter extends FormAuthenticationFilter {
	
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletResponse req  = (HttpServletResponse) response;
		
		req.sendError(406,"请登录");
		return false;
	}
}
