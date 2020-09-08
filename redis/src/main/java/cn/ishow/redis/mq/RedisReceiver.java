package cn.ishow.redis.mq;

import cn.ishow.redis.annotation.RedisMQ;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author yinchong
 * @create 2020/8/29 9:47
 * @description
 */
@Slf4j
public class RedisReceiver implements Receiver<String> , InitializingBean, BeanFactoryAware {

    private List<ProxyReceiver> receiverList = new LinkedList<>();

    private DefaultListableBeanFactory beanFactory;


    @Override
    public void receiveMessage(String message){
       RedisMQMessage redisMQMessage = JSON.parseObject(message,RedisMQMessage.class);
       for(ProxyReceiver proxy:receiverList){
           if(proxy.matchTopic(redisMQMessage.getTopic())){
               proxy.receiveMessage(redisMQMessage.getBody());
           }
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, Object> beansWithAnnotation = beanFactory.getBeansWithAnnotation(RedisMQ.class);
        if(!CollectionUtils.isEmpty(beansWithAnnotation)){
            for(Map.Entry<String,Object> entry:beansWithAnnotation.entrySet()){
                String name = entry.getKey();
                Object value = entry.getValue();
                if(! (value instanceof Receiver)){
                    log.warn("bean:{} is not type of {}",name,Receiver.class);
                    continue;
                }

                ProxyReceiver proxyReceiver = new ProxyReceiver((Receiver) value);
                RedisMQ redisMQ = value.getClass().getAnnotation(RedisMQ.class);
                proxyReceiver.batchAddTopic(redisMQ.topics());
                receiverList.add(proxyReceiver);
            }
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }
}
