package cn.ishow.boot.rocketmq.mq;

import cn.ishow.boot.rocketmq.config.RocketMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author yinchong
 * @create 2020/8/29 17:36
 * @description
 */
@Slf4j
public class RocketMQTemplate implements InitializingBean {

    private DefaultMQProducer defaultMQProducer;

    @Autowired
    private RocketMqConfig config;
    @Value("${spring.application.name}")
    private String application;


    @Override
    public void afterPropertiesSet() throws Exception {
        defaultMQProducer = new DefaultMQProducer();
        defaultMQProducer.setInstanceName("rocketmq-" + application);
        defaultMQProducer.setNamesrvAddr(config.getNameserver());
        defaultMQProducer.setProducerGroup("p_" + application);
        defaultMQProducer.start();
    }

    public void send(String topic, String tag, String key, String body) {
        Message message = new Message();
        message.setTags(tag);
        message.setTopic(topic);
        message.setKeys(key);
        message.setBody(body.getBytes());
        try {
            SendResult result = defaultMQProducer.send(message);
            log.info("send msg result:{}", result);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("send msg error {} error:", body, e);
        }
    }


    public void send(String topic, String tag, String body) {
        Message message = new Message();
        message.setTags(tag);
        message.setTopic(topic);
        message.setBody(body.getBytes());
        try {
            SendResult result = defaultMQProducer.send(message);
            log.info("send msg result:{}", result);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("send msg error {} error:", body, e);
        }
    }

    public void send(String topic, String body) {
        send(topic, "*", body);
    }


    public void destroy() {
        log.info("begin destroy producer");
        defaultMQProducer.shutdown();
    }
}
