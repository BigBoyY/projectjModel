package com.yyjj.config;
import java.sql.PreparedStatement;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;

/**
 * mybatis 拦截器
 * @author Administrator
 *
 */
@Intercepts({
    @Signature(type = ParameterHandler.class, method = "setParameters", args = PreparedStatement.class),
    @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class MybatisSqlInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		
		//Object params [] = invocation.getArgs();
		//for (Object object : params) {
			//System.out.println(object);
		//}
		
		return invocation.proceed();
	}
	@Override
	public Object plugin(Object target) {
		// TODO Auto-generated method stub
		return Plugin.wrap(target, this);
	}
	
	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub
		Interceptor.super.setProperties(properties);
	}
}
