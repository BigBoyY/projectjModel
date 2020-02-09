package com.yyjj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

/**
 * 配置拦截器
 * @author Administrator
 *
 */
@Configuration
public class MyBatisPlusConfig {
    
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
    
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return new ConfigurationCustomizer() {
			
			@Override
			public void customize(MybatisConfiguration configuration) {              
                configuration.addInterceptor(new MybatisSqlInterceptor());
				
			}
		};
    }
    
}
