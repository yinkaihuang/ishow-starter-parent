package cn.ishow.common.ready;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @author yinchong
 * @create 2019/11/11 18:29
 * @description
 */
public class SpringBootReady implements ApplicationListener<ApplicationEnvironmentPreparedEvent>, Ordered {
    private static volatile boolean ready = false;

    static {
        ready = !isCloud();
    }

    /**
     * 监听事件在Cloud之后
     *
     * @param applicationEvent
     */
    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent applicationEvent) {
        ready = true;
    }

    public static boolean isReady() {
        return ready;
    }

    /**
     * 判断当前启动程序是Cloud还是Boot
     *
     * @return
     */
    private static boolean isCloud() {
        try {
            Class.forName("org.springframework.cloud.bootstrap.BootstrapApplicationListener");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE + 6;
    }
}
