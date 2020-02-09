package com.yyjj.shiro;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestPermissionsAuthorizationFilter extends PermissionsAuthorizationFilter {
	 private static final Logger log = LoggerFactory.getLogger(RestPermissionsAuthorizationFilter.class);

	    @Override
	    protected boolean pathsMatch(String path, ServletRequest request) {
	        String requestURI = this.getPathWithinApplication(request);
	        
	        String[] strings = path.split("==");

	        if (strings.length <= 1) {
	            // 普通的 URL, 正常处理
	            return this.pathsMatch(strings[0], requestURI);
	        } else {
	            // 获取当前请求的 http method.
	            String httpMethod = WebUtils.toHttp(request).getMethod().toUpperCase();
	            	
	            // 匹配当前请求的 http method 与 过滤器链中的的是否一致
	            return httpMethod.equals(strings[1].toUpperCase()) && this.pathsMatch(strings[0], requestURI);
	        }
	    }
	    
	    @Override
	    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {

	        Subject subject = getSubject(request, response);
	        String[] perms = (String[]) mappedValue;      
	        if (perms != null && perms.length > 0) {
	        	//将源码全等关系改为包含关系
	        	for (String perm : perms) {
					if(subject.isPermitted(perm)) {
						return true;
					 }
				}
//	            if (perms.length == 1) {
//	                if (!subject.isPermitted(perms[0])) {
//	                    isPermitted = false;
//	                }
//	            } else {
//	                if (!subject.isPermittedAll(perms)) {
//	                    isPermitted = false;
//	                }
//	            }
	        }
	        log.info("无权限或未登录");
	        return false;
	    }
	    
//	    /**
//	     * 当没有权限被拦截时:
//	     *          如果是 AJAX 请求, 则返回 JSON 数据.
//	     *          如果是普通请求, 则跳转到配置 UnauthorizedUrl 页面.
//	     */
//	    @Override
//	    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
//	        Subject subject = getSubject(request, response);
//	        // 如果未登录
//	        if (subject.getPrincipal() == null) {
//	            // AJAX 请求返回 JSON
//	            if (im.zhaojun.util.WebUtils.isAjaxRequest(WebUtils.toHttp(request))) {
//	                if (log.isDebugEnabled()) {
//	                    log.debug("用户: [{}] 请求 restful url : {}, 未登录被拦截.", subject.getPrincipal(), this.getPathWithinApplication(request));                }
//	                Map<String, Object> map = new HashMap<>();
//	                map.put("code", -1);
//	                im.zhaojun.util.WebUtils.writeJson(map, response);
//	            } else {
//	                // 其他请求跳转到登陆页面
//	                saveRequestAndRedirectToLogin(request, response);
//	            }
//	        } else {
//	            // 如果已登陆, 但没有权限
//	            // 对于 AJAX 请求返回 JSON
//	            if (im.zhaojun.util.WebUtils.isAjaxRequest(WebUtils.toHttp(request))) {
//	                if (log.isDebugEnabled()) {
//	                    log.debug("用户: [{}] 请求 restful url : {}, 无权限被拦截.", subject.getPrincipal(), this.getPathWithinApplication(request));
//	                }
//
//	                Map<String, Object> map = new HashMap<>();
//	                map.put("code", -2);
//	                map.put("msg", "没有权限啊!");
//	                im.zhaojun.util.WebUtils.writeJson(map, response);
//	            } else {
//	                // 对于普通请求, 跳转到配置的 UnauthorizedUrl 页面.
//	                // 如果未设置 UnauthorizedUrl, 则返回 401 状态码
//	                String unauthorizedUrl = getUnauthorizedUrl();
//	                if (StringUtils.hasText(unauthorizedUrl)) {
//	                    WebUtils.issueRedirect(request, response, unauthorizedUrl);
//	                } else {
//	                    WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
//	                }
//	            }
//
//	        }
//	        return false;
//	    }

}
