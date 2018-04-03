package com.shop.base.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

@Component
public class RedisClient {

    private static Logger logger = LoggerFactory.getLogger(RedisClient.class);

    @Autowired
    private JedisPool     jedisPool;

    public void set(String key, Object value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, JSONObject.toJSONString(value));
        } finally {
            //返还到连接池  
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void setex(String key, int seconds, Object value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.setex(key, seconds, JSONObject.toJSONString(value));
        } finally {
            //返还到连接池  
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        } finally {
            //返还到连接池  
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public <T> T get(String key, T t) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String value = jedis.get(key);
            return JSON.parseObject(value, new TypeReference<T>() {
            });
        } catch (Exception e) {
            logger.error("redis解析value对象失败，class={}", t);
            return null;
        } finally {
            //返还到连接池  
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void del(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
        } finally {
            //返还到连接池  
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        } finally {
            //返还到连接池  
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public long ttl(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.ttl(key);
        } finally {
            //返还到连接池  
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public long expire(String key, int seconds) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.expire(key, seconds);
        } finally {
            //返还到连接池  
            if (jedis != null) {
                jedis.close();
            }
        }
    }

}