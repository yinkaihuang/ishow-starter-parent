package cn.ishow.redis.mq;

import cn.ishow.redis.util.ReflectUtils;
import com.alibaba.fastjson.JSON;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author yinchong
 * @create 2020/8/29 9:59
 * @description
 */
public class ProxyReceiver implements Receiver {

    private Receiver receiver;
    private List<String> topics = new LinkedList<>();

    private Class clazz;

    public ProxyReceiver(Receiver receiver) {
        this.receiver = receiver;
        assetClassType(receiver);
    }

    private void assetClassType(Receiver receiver) {
        clazz = ReflectUtils.getInterfaceT(receiver, 0);
    }

    @Override
    public void receiveMessage(Object message) {
        if (clazz.equals(String.class)) {
            receiver.receiveMessage(message);
        } else {
            receiver.receiveMessage(JSON.parseObject(String.valueOf(message), clazz));
        }
    }

    public void batchAddTopic(String[] topics) {
        this.topics.addAll(Arrays.asList(topics));
    }

    public boolean matchTopic(String topic) {
        if (topics.contains("*")) {
            return true;
        }
        if (topics.contains(topic)) {
            return true;
        }
        return false;
    }
}
