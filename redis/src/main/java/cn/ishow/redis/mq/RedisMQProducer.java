package cn.ishow.redis.mq;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author yinchong
 * @create 2020/8/29 10:15
 * @description
 */
public class RedisMQProducer {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Value("${spring.application.name}")
    private String application;

    public void send(String topic,String message){
        RedisMQMessage msg = new RedisMQMessage(topic,message);
        redisTemplate.convertAndSend("redis_"+application, JSON.toJSONString(msg));
    }
}
