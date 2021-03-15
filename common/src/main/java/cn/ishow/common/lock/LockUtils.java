package cn.ishow.common.lock;

import cn.ishow.common.util.BeanFactoryUtils;
import org.redisson.Redisson;
import org.redisson.api.RLock;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author yinchong
 * @create 2021/3/12 20:36
 * @description 分布式锁
 */
public class LockUtils {

    private static volatile Redisson redisson;


    public static <T> T invokeInLock(String key, long wait, long lease, TimeUnit timeUnit, Callable<T> call) {
        init();
        RLock lock = redisson.getLock(key);
        try {

            boolean tryLock = lock.tryLock(wait, lease, timeUnit);
            if (!tryLock) {
                throw new RuntimeException("get lock fail");
            }

            return call.call();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.forceUnlock();
            }
        }

    }


    private static void init() {
        if (redisson != null) {
            return;
        }
        synchronized (LockUtils.class) {
            if (redisson == null) {
                redisson = BeanFactoryUtils.getBean(Redisson.class);
            }
        }
    }
}
