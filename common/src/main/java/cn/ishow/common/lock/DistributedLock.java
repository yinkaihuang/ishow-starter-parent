package cn.ishow.common.lock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 86322
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DistributedLock {
    String key() ;

    //如果非0.name  索引位置.属性
    String[] args() default {};

    int waitTime() default 1000;

    int leaseTime() default 60000;

}
