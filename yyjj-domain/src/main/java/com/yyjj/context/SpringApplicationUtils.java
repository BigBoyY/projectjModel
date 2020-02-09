package com.yyjj.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringApplicationUtils implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringApplicationUtils.applicationContext = applicationContext;
	}
	
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanId) {
     return (T)applicationContext.getBean(beanId);
    }
   
    public static <T> T getBean(Class<T> requiredType){
    	return (T)applicationContext.getBean(requiredType);
    }
    
    public static ApplicationContext getApplicationContext() {
    	return applicationContext;
    }
}
