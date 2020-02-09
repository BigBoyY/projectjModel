package com.yyjj.transaction;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Aspect
@Component
public class TransactionAspect {
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	TransactionStatus status = null;
	private static final Logger log = LoggerFactory.getLogger(TransactionAspect.class);

	// 定义切点 --> 拦截MaintainLog
	@Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)|| "
			+ "@annotation(org.springframework.web.bind.annotation.PutMapping) || "
			+ "@annotation(org.springframework.web.bind.annotation.DeleteMapping) ")
	public void transactionAspect() {
	}
	
	/**
	 * 拦截方法执行之前
	 * @param pjp
	 */
	@Before("transactionAspect()")
	public void doBefore(JoinPoint pjp){
		log.info("开启事务");
		status = this.transaction();
	}
	
	/**
	 * 拦截方法执行 return 前
	 * @param pjp  切点
	 * @param rvt  返回的值
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	@AfterReturning(value = "transactionAspect()", returning = "rvt")
	public void doAfterReturning(JoinPoint pjp, Object rvt) throws NoSuchFieldException, SecurityException {
		log.info("方法返回前，提交 事务"); 
		transactionManager.commit(status);
	}
	
	/**
	 * 拦截的方法出现异常时
	 * @param joinPoint
	 * @param e
	 */
	@AfterThrowing(value = "transactionAspect()",throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint,Throwable e) {
		 log.info("出现异常，回滚事务"); 
		 transactionManager.rollback(status);
    }
	
	private TransactionStatus transaction(){
	    DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
	    defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
	    TransactionStatus status = transactionManager.getTransaction(defaultTransactionDefinition);
	    return status;
	}
}
