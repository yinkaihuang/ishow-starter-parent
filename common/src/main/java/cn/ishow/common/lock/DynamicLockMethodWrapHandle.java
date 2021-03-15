package cn.ishow.common.lock;

import cn.ishow.common.util.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author yinchong
 * @create 2021/3/12 21:03
 * @description
 */
@Aspect
public class DynamicLockMethodWrapHandle {


    @Around("@annotation(lock)")
    public Object process(ProceedingJoinPoint point, DistributedLock lock) {

        String key = lock.key();
        int waitTime = lock.waitTime();
        int leaseTime = lock.leaseTime();
        if (lock.args() != null && lock.args().length > 0) {
            List<String> paramList = new LinkedList<>();
            for (String arg : lock.args()) {
                paramList.add(argValue(arg, point.getArgs()));
            }
            key = String.format(key, paramList.toArray());
        }
        return LockUtils.invokeInLock(key, waitTime, leaseTime, TimeUnit.MILLISECONDS, new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                try {
                    return point.proceed();
                } catch (Throwable throwable) {
                    throw new RuntimeException(throwable);
                }
            }
        });
    }


    private String argValue(String argName, Object[] argValues) {
        int index = 0;
        String fieldName;
        if (!argName.contains(".")) {
            boolean isNumber = StringUtils.isInteger(argName);
            if (isNumber) {
                return String.valueOf(argValues[Integer.valueOf(argName)]);
            }
            fieldName = argName;
        } else {
            String[] split = argName.split("\\.");
            index = Integer.valueOf(split[0]);
            fieldName = split[1];
        }
        Object target = argValues[index];
        String name = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        try {
            Method method = target.getClass().getMethod("get" + name);
            return String.valueOf(method.invoke(target));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
