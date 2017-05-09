package com.softisland.config;
import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.Configuration;   
import org.springframework.data.redis.connection.RedisConnectionFactory;  
import org.springframework.data.redis.core.RedisTemplate;  
import org.springframework.data.redis.core.StringRedisTemplate;  


@Configuration
public class RedisConfig  {
	    @Bean  
	    public RedisTemplate<String, String> redisTemplate(  
	            RedisConnectionFactory factory) {  
	        StringRedisTemplate template = new StringRedisTemplate(factory);  
	     
	        return template;  
	    }
}
