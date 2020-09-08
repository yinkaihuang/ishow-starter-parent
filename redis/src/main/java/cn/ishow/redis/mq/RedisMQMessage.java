package cn.ishow.redis.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yinchong
 * @create 2020/8/29 9:50
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedisMQMessage {
    private String topic;
    private String body;
}
