package cn.ishow.boot.rocketmq.mq;

import cn.ishow.boot.rocketmq.annotation.Basic;
import cn.ishow.boot.rocketmq.annotation.RocketMQ;
import cn.ishow.boot.rocketmq.config.RocketMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import javax.annotation.PreDestroy;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * RocketMQ 消费者收集器
 * @author yinchong
 * @create 2020/8/29 17:18
 * @description
 */
@Slf4j
public class RocketMQConsumeCollector implements InitializingBean, BeanFactoryAware, EnvironmentAware {

    private List<MQPushConsumer> list = new LinkedList<>();

    private DefaultListableBeanFactory beanFactory;

    private Environment environment;

    @Autowired
    private RocketMqConfig config;


    @PreDestroy
    public void destroy() {
        log.info("begin destroy consumer");
        for (MQPushConsumer consumer : list) {
            consumer.shutdown();
        }
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        Map<String, Object> beansWithAnnotation = beanFactory.getBeansWithAnnotation(RocketMQ.class);
        for (Map.Entry<String, Object> entry : beansWithAnnotation.entrySet()) {
            Object value =entry.getValue();
            RocketMQ rocketMQ = value.getClass().getAnnotation(RocketMQ.class);
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(rocketMQ.groupId());
            //设置地址
            consumer.setNamesrvAddr(config.getNameserver());
            //设置实例名称
            consumer.setInstanceName(entry.getKey());
            Basic[] topics = rocketMQ.basics();
            //订阅主题和标签
            for (Basic topic : topics) {
                String topicName = topic.topic();
                String tagName = topic.tags();
                consumer.subscribe(getFinalValue(topicName), getFinalValue(tagName));
            }
            if(value instanceof MessageListenerConcurrently){
                consumer.registerMessageListener((MessageListenerConcurrently)value);
            }else if(value instanceof MessageListenerOrderly){
                consumer.registerMessageListener((MessageListenerOrderly)value);
            }else{
                log.error("rocketMQ consumer must implement MessageListenerConcurrently or MessageListenerOrderly {}",value.getClass().getName());
                throw new UnsupportedOperationException("rocketMQ consumer must implement MessageListenerConcurrently or MessageListenerOrderly :"+value.getClass().getName());

            }
            consumer.start();
            list.add(consumer);
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
    
    
    private String getFinalValue(String value){
        if(value.startsWith("${")&&value.endsWith("}")){
            return environment.getProperty(value.replace("${","").replace("}",""));
        }
        return value;
    }
}
