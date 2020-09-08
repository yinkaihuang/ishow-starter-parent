package cn.ishow.redis;

import cn.ishow.common.inject.AbstractPropertiesInject;

import java.util.Map;

/**
 * @author yinchong
 * @create 2019/11/11 22:40
 * @description 默认Redis配置注册类
 */
public class RedisDefaultPropertiesInject extends AbstractPropertiesInject {


    @Override
    public void onDevProfile(Map<String, Object> propertiesMap) {
        propertiesMap.put("spring.redis.database",0);
        propertiesMap.put("spring.redis.host","127.0.0.1");
        propertiesMap.put("spring.redis.port",6379);
        propertiesMap.put("spring.redis.password","");
        propertiesMap.put("spring.redis.pool.max-active",200);
        propertiesMap.put("spring.redis.pool.max-wait",-1);
        propertiesMap.put("spring.redis.pool.max-idle",10);
        propertiesMap.put("spring.redis.pool.min-idle",5);
        propertiesMap.put("spring.redis.timeout",10000);
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
