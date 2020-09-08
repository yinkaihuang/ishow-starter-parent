package cn.ishow.boot.rocketmq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yinchong
 * @create 2020/8/29 17:12
 * @description
 */
@Data
@ConfigurationProperties(prefix = "spring.rocketmq")
public class RocketMqConfig {
    private String nameserver;
}
