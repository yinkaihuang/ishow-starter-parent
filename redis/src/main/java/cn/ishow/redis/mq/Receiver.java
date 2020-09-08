package cn.ishow.redis.mq;

public interface Receiver<T> {

    void receiveMessage(T message);
}
