package cn.ishow.boot.rocketmq.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yinchong
 * @create 2020/8/29 17:10
 * @description
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Basic {
    //主题
    String topic();
    //标签
    String tags() default "*";
}
