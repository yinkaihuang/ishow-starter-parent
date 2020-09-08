package cn.ishow.boot.rocketmq.configuration;

import cn.ishow.boot.rocketmq.config.RocketMqConfig;
import cn.ishow.boot.rocketmq.mq.RocketMQConsumeCollector;
import cn.ishow.boot.rocketmq.mq.RocketMQTemplate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author yinchong
 * @create 2020/8/29 17:40
 * @description
 */
@EnableConfigurationProperties(value = {RocketMqConfig.class})
public class RocketMQConfiguration {

    @Bean
    public RocketMQTemplate rocketMQTemplate() {
        return new RocketMQTemplate();
    }

    @Bean
    public RocketMQConsumeCollector consumeCollector() {
        return new RocketMQConsumeCollector();
    }
}
