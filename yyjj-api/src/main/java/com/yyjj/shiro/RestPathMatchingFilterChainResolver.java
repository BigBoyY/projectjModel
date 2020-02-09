package com.yyjj.shiro;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestPathMatchingFilterChainResolver extends PathMatchingFilterChainResolver {
	//private static final Logger LOGGER = LoggerFactory.getLogger(RestPathMatchingFilterChainResolver.class);
	private static final Logger log = LoggerFactory.getLogger(RestPathMatchingFilterChainResolver.class);

    public RestPathMatchingFilterChainResolver() {
        super();
    }

    public RestPathMatchingFilterChainResolver(FilterConfig filterConfig) {
        super(filterConfig);
    }

    /* *
     * @Description 重写filterChain匹配
     * @Param [request, response, originalChain]
     * @Return javax.servlet.FilterChain
     */
    @Override
    public FilterChain getChain(ServletRequest request, ServletResponse response, FilterChain originalChain) {
        FilterChainManager filterChainManager = this.getFilterChainManager();
        if (!filterChainManager.hasChains()) {
            return null;
        } else {
            String requestURI = this.getPathWithinApplication(request);
            for (String pathPattern : filterChainManager.getChainNames()) {
            	//截取url==POST 为url
                String[] pathPatternArray = pathPattern.split("==");

                // 只用过滤器链的 URL 部分与请求的 URL 进行匹配
                if (pathMatches(pathPatternArray[0], requestURI)) {
                    if (log.isTraceEnabled()) {
                        log.trace("Matched path pattern [" + pathPattern + "] for requestURI [" + requestURI + "].  " +
                                "Utilizing corresponding filter chain...");
                    }
                    return filterChainManager.proxy(originalChain, pathPattern);
                }
            }
            
            return null;
            
//            Iterator var6 = filterChainManager.getChainNames().iterator();
//
//            String pathPattern;
//            boolean flag = true;
//            String[] strings = null;
//            do {
//                if (!var6.hasNext()) {
//                    return null;
//                }
//
//                pathPattern = (String)var6.next();
//                
//                strings = pathPattern.split("==");
//                if (strings.length == 2) {
//                    // 分割出url+httpMethod,判断httpMethod和request请求的method是否一致,不一致直接false
//                    if (WebUtils.toHttp(request).getMethod().toUpperCase().equals(strings[1].toUpperCase())) {
//                        flag = false;
//                    } else {
//                        flag = true;
//                    }
//                } else {
//                    flag = false;
//                }
//                pathPattern = strings[0];
//            } while(!this.pathMatches(pathPattern, requestURI) || flag);
//
//            if (LOGGER.isTraceEnabled()) {
//                LOGGER.trace("Matched path pattern [" + pathPattern + "] for requestURI [" + requestURI + "].  Utilizing corresponding filter chain...");
//            }
//            if (strings.length == 2) {
//                pathPattern = pathPattern.concat("==").concat(WebUtils.toHttp(request).getMethod().toUpperCase());
//            }
//
//            return filterChainManager.proxy(originalChain, pathPattern);
        }
    }
}
