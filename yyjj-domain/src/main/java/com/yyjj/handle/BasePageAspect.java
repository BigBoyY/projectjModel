package com.yyjj.handle;

import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.yyjj.context.BasePageVOContextHolder;
import com.yyjj.service.BasePageVO;
import com.yyjj.service.OrderItems;

/**
 * 拦截获取page的请求值
 * 
 * @author doyle
 *
 */
@Aspect
@Component
public class BasePageAspect {

    private static final Logger logger = LoggerFactory.getLogger(BasePageAspect.class);

    private static final String PAGE_NAME = "page";

    private static final String PAGE_SIZE_NAME = "pageSize";
    
    private static final String SORT= "orders";
    
    private static final String SORT_ASC = "asc";
    private static Pattern humpPattern = Pattern.compile("[A-Z]");
    private static final String DEFAULT_COLUMN = "create_time";
    // 定义切点 --> 拦截GetMapping
    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void webLog() {
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.nonNull(sra)) {
            HttpServletRequest request = sra.getRequest();
            Enumeration<String> em = request.getParameterNames();
           
            BasePageVO pageVO = null;
            while (em.hasMoreElements()) {
                String name = em.nextElement();
                String value = request.getParameter(name);
                if (PAGE_NAME.equals(name)) {
                    pageVO = Objects.isNull(pageVO) ? new BasePageVO() : pageVO;
                    pageVO.setPage(Long.valueOf(value));
                }

                if (PAGE_SIZE_NAME.equals(name)) {
                    pageVO = Objects.isNull(pageVO) ? new BasePageVO() : pageVO;
                    
                    pageVO.setPageSize(Long.valueOf(value));
                }
                
                if(SORT.equals(name)) {
                	pageVO = Objects.isNull(pageVO) ? new BasePageVO() : pageVO;
                	pageVO.setOrders(CollectionUtils.isEmpty(pageVO.getOrders()) ? new LinkedList<>() : pageVO.getOrders());
                	value = humpToLine2(value);
                	if(pageVO.getOrders().size() > 0){
                		pageVO.getOrders().getFirst().setColumn(value);
                	}else {
                		pageVO.getOrders().add(new OrderItems(value));
                	}               	               	
                }
                
                if(SORT_ASC.equals(name)) {
                	pageVO = Objects.isNull(pageVO) ? new BasePageVO() : pageVO;
                	pageVO.setOrders(CollectionUtils.isEmpty(pageVO.getOrders()) ? new LinkedList<>() : pageVO.getOrders());
                	
                	if(Objects.nonNull(pageVO.getOrders().getFirst())){
                		pageVO.getOrders().getFirst().setAsc(Boolean.valueOf(value));
                	}else {
                		pageVO.getOrders().add(new OrderItems(DEFAULT_COLUMN));
                	}   
                }
                
            }

            logger.debug("pageVO: {}", pageVO);
            BasePageVOContextHolder.setBasePageVO(pageVO);
        }

        return pjp.proceed();
    }
    private  String humpToLine2(String str) {
	      Matcher matcher = humpPattern.matcher(str);
	     StringBuffer sb = new StringBuffer();
	     while (matcher.find()) {
	          matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
	      }
	       matcher.appendTail(sb);
	     return sb.toString();
	 }
}