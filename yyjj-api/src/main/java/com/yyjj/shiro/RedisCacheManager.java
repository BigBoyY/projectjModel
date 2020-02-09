package com.yyjj.shiro;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisCacheManager extends AbstractCacheManager {
	
	private RedisTemplate<byte[],byte[]> redisTemplate;

    public RedisCacheManager(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }
	
	 //为了个性化配置redis存储时的key，我们选择了加前缀的方式，所以写了一个带名字及redis操作的构造函数的Cache类
	@Override
	protected Cache createCache(String arg0) throws CacheException {
		// TODO Auto-generated method stub
		return new ShiroRedisCache(redisTemplate,arg0);
	}

}
