package cn.ishow.mybatis.boot.loader;


import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;

/**
 * @author yinchong
 * @create 2020/3/22 11:28
 * @description
 */
public class SpringClassReLoader {

    /** org.apache.ibatis.executor.resultset.DefaultResultSetHandler
     * 重新加载，类对象到内存中
     * @param bytes
     */
    public static void reload(String name,byte[] bytes){
        ClassLoader loader = ClassUtils.getDefaultClassLoader();
        try {
            Method defineClass = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class);
            defineClass.setAccessible(true);
            defineClass.invoke(loader,name,bytes,0,bytes.length);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
