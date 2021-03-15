package cn.ishow.common.run;

import cn.ishow.common.util.BeanFactoryUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author yinchong
 * @create 2021/3/12 20:51
 * @description
 */
public class MethodStartAutoRefresh implements CommandLineRunner, Ordered {
    @Override
    public void run(String... args) throws Exception {
        Map<String, Object> map = BeanFactoryUtils.listBeanWithAnnotation(StartRefresh.class);
        if (CollectionUtils.isEmpty(map)) {
            return;
        }
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object value = entry.getValue();
            Method[] methods = value.getClass().getMethods();
            if (methods == null || methods.length == 0) {
                continue;
            }
            for (Method method : methods) {
                AutoRefresh annotation = method.getAnnotation(AutoRefresh.class);
                if (annotation == null) {
                    continue;
                }
                method.invoke(value);
            }
        }
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
