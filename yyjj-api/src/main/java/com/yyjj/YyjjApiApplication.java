package com.yyjj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan(basePackages= {"com.yyjj.dao"})
@EnableCaching
public class YyjjApiApplication {
	
	public static void main(String[] args) {
		System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow","|[]");
		SpringApplication.run(YyjjApiApplication.class, args);
		}
	}


