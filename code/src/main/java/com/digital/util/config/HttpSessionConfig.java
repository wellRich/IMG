package com.digital.util.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @Author: zhanghpj
 * @Description: TODO
 * @Date: Create in 15:36 2018/1/31
 * @Modified_By:
 */

@EnableRedisHttpSession
public class HttpSessionConfig {

    @Bean
    public JedisConnectionFactory connectionFactory(){
        return new JedisConnectionFactory();
    }
}


