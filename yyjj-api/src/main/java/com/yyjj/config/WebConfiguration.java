package com.yyjj.config;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

/**
 * 修改相关spring boot配置类信息
 * 
 * @author doyle
 *
 */
@Configuration
public class WebConfiguration extends WebMvcConfigurationSupport {
    
	@Value("${upload.fileLibraryPath}")
	String savePath;
	
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (int i = 0; i < converters.size(); i++) {
            // 将jackson的时间格式化为带有标准时区的格式 
        	
            if (converters.get(i) instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter converter = (MappingJackson2HttpMessageConverter) converters.get(i);
                
                ObjectMapper objectMapper = converter.getObjectMapper();
                
                SimpleModule simpleModule = new SimpleModule("JsonMapSerializer", Version.unknownVersion());
          
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                simpleModule.addSerializer(new LocalDateTimeSerializer(dateTimeFormatter));
                
                // 忽略字段为null的数据
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

                objectMapper.registerModule(simpleModule);
                break;
            }
        }
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //重写这个方法，映射静态资源文件
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/public/")
                .addResourceLocations("file:"+savePath);

        super.addResourceHandlers(registry);
    }
    
     
    @Bean
    public CorsConfiguration corsConfiguration() {
      CorsConfiguration corsConfiguration = new CorsConfiguration();
      //实际请求中允许携带的首部字段
      corsConfiguration.addAllowedHeader("*");
      //允许那些域跨域访问
      corsConfiguration.addAllowedOrigin("*");
      //允许那些请求方法
      corsConfiguration.addAllowedMethod("*");
      return corsConfiguration;
    }
    
    @Bean
    public CorsFilter corsFilter(CorsConfiguration corsConfiguration) {
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", corsConfiguration); // 4
      return new CorsFilter(source);
    }
    
}
