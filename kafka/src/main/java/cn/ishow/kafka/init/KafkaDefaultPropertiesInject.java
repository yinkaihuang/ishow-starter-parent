package cn.ishow.kafka.init;

import cn.ishow.common.inject.AbstractPropertiesInject;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Map;

/**
 * @author yinchong
 * @create 2019/11/11 21:37
 * @description
 */
public class KafkaDefaultPropertiesInject extends AbstractPropertiesInject {


    @Override
    public void onDevProfile(Map<String, Object> propertiesMap) {
        propertiesMap.put("spring.kafka.bootstrap-servers", "127.0.0.1:9092");
        //消费端
        propertiesMap.put("spring.kafka.consumer.group-id", "dev");
        propertiesMap.put("spring.kafka.consumer.key-deserializer", StringDeserializer.class.getName());
        propertiesMap.put("spring.kafka.consumer.value-deserializer", StringDeserializer.class.getName());
        propertiesMap.put("spring.kafka.consumer.enable-auto-commit", "true");
        propertiesMap.put("spring.kafka.consumer.auto-commit-interval", "1000");
        propertiesMap.put("spring.kafka.consumer.auto-offset-reset", "latest");
        //生成端
        propertiesMap.put("spring.kafka.producer.acks", "1");
        propertiesMap.put("spring.kafka.producer.retries", "3");
        propertiesMap.put("spring.kafka.producer.key-serializer", StringSerializer.class.getName());
        propertiesMap.put("spring.kafka.producer.value-serializer", StringSerializer.class.getName());
    }

    @Override
    public void onStgProfile(Map<String, Object> propertiesMap) {

    }

    @Override
    public void onPrdProfile(Map<String, Object> propertiesMap) {

    }

    @Override
    public void onAllProfile(Map<String, Object> propertiesMap) {

    }
}
