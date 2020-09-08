package cn.ishow.web.initialize;

import cn.ishow.common.inject.AbstractPropertiesInject;

import java.util.Map;

/**
 * @author yinchong
 * @create 2019/11/11 21:00
 * @description
 */
public class WebDefaultPropertiesInject extends AbstractPropertiesInject {


    @Override
    public void onDevProfile(Map<String, Object> propertiesMap) {
        propertiesMap.put("spring.mvc.throw-exception-if-no-handler-found", true);
        propertiesMap.put("spring.resources.add-mappings", false);
        propertiesMap.put("spring.http.encoding.charset", "utf-8");
        propertiesMap.put("spring.http.encoding.force", true);
        propertiesMap.put("spring.http.encoding.enabled", true);
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
