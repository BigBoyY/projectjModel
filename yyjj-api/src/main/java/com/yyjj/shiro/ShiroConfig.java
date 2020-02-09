package com.yyjj.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yyjj.service.system.MenuService;
 
/**
 * Created by Administrator on 2020/1/7
 */
@Configuration
public class ShiroConfig {
 
//    @Value("${spring.redis.shiro.host}")
//    private String host;
//    @Value("${spring.redis.shiro.port}")
//    private int port;
//    @Value("${spring.redis.shiro.timeout}")
//    private int timeout;
//    @Value("${spring.redis.shiro.password}")
//    private String password;
// 	
	@Autowired
	MenuService menuService;
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager securityManager) {
    	//使用自定义过滤器RestShiroFilterFactoryBean
        ShiroFilterFactoryBean shiroFilterFactoryBean = new RestShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //重写perms过滤器
        shiroFilterFactoryBean.getFilters().put("authc", new MyAuthcFilter());
        shiroFilterFactoryBean.getFilters().put("perms", new RestPermissionsAuthorizationFilter());
        
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        //注意过滤器配置顺序 不能颠倒
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了，登出后跳转配置的loginUrl
        initAnonPath(filterChainDefinitionMap);
        
        menuService.initAuthorization(filterChainDefinitionMap);     
        filterChainDefinitionMap.put("/**", "anon");
        //配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
        shiroFilterFactoryBean.setLoginUrl("/unauth");
        // 登录成功后要跳转的链接
        //shiroFilterFactoryBean.setSuccessUrl("/index");
        //未授权界面;
        //shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }
    
    /**
     * 初始化匿名路径
     */
    private void initAnonPath( Map<String, String> filterChainDefinitionMap) {
    	// 配置不会被拦截的链接 顺序判断
    	filterChainDefinitionMap.put("/webSocket", "anon");  
    	filterChainDefinitionMap.put("/doc/**", "anon");   
    	filterChainDefinitionMap.put("/user/logout", "anon");      
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/unauthorized", "anon");
        filterChainDefinitionMap.put("/unauth", "anon");
        filterChainDefinitionMap.put("/file/**/download", "anon");
        filterChainDefinitionMap.put("/user/login", "anon");
    }
   

    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     * ）
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(1);//散列的次数，比如散列两次，相当于 md5(md5(""));
        return hashedCredentialsMatcher;
    }
 
    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }
   
 
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager());
        // 自定义缓存实现 使用redis
        //securityManager.setCacheManager(cacheManager());
        return securityManager;
    }
 
    //自定义sessionManager
    @Bean
    public SessionManager sessionManager() {
        MySessionManager mySessionManager = new MySessionManager();
        mySessionManager.setGlobalSessionTimeout(7200000L);
       
       // mySessionManager.setSessionDAO(redisSessionDAO());
        return mySessionManager;
    }
 
    /**
     * 配置shiro redisManager
     * <p>
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
//    public RedisManager redisManager() {
//        RedisManager redisManager = new RedisManager();
//        redisManager.setHost(host);
//        redisManager.setPort(port);
//        redisManager.setExpire(1800);// 配置缓存过期时间
//        redisManager.setTimeout(timeout);
//        redisManager.setPassword(password);
//        return redisManager;
//    }
 
    /**
     * cacheManager 缓存 redis实现
     * <p>
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
//    @Bean
//    public RedisCacheManager cacheManager() {
//        RedisCacheManager redisCacheManager = new RedisCacheManager();
//        redisCacheManager.setRedisManager(redisManager());
//        return redisCacheManager;
//    }
 
    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * <p>
     * 使用的是shiro-redis开源插件
     */
//    @Bean
//    public RedisSessionDAO redisSessionDAO() {
//        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
//        redisSessionDAO.setRedisManager(redisManager());
//        return redisSessionDAO;
//    }
 
    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
 
    /**
     * 注册全局异常处理
     * @return
     */
//    @Bean(name = "exceptionHandler")
//    public HandlerExceptionResolver handlerExceptionResolver() {
//        return new MyExceptionHandler();
//    }
}