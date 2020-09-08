package cn.ishow.common.inject;
import cn.ishow.common.constant.EnvConstant;
import cn.ishow.common.ready.SpringBootReady;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yinchong
 * @create 2019/11/11 21:02
 * @description
 */
@Slf4j
public abstract class AbstractPropertiesInject implements EnvironmentPostProcessor {
    public static final String DEFAULT_PROPERTIES = "default-properties";


    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        //如果boot未准备好直接放行
        if (!SpringBootReady.isReady()) {
            return;
        }
        String[] actives = environment.getActiveProfiles();
        List<String> activeList = Arrays.asList(actives);
        if (CollectionUtils.isEmpty(activeList)) {
            log.info("no find active profile use default :{}", EnvConstant.DEV);
            activeList = Arrays.asList(EnvConstant.DEV);
        }
        Map<String, Object> propertiesMap = createOrGetMap(environment);
        if (activeList.contains(EnvConstant.DEV)) {
            onDevProfile(propertiesMap);
        } else if (activeList.contains(EnvConstant.TEST)) {
            onStgProfile(propertiesMap);
        } else if (activeList.contains(EnvConstant.PRD)) {
            onPrdProfile(propertiesMap);
        }
        onAllProfile(propertiesMap);
    }


    public abstract void onDevProfile(Map<String, Object> propertiesMap);

    public abstract void onStgProfile(Map<String, Object> propertiesMap);

    public abstract void onPrdProfile(Map<String, Object> propertiesMap);

    public abstract void onAllProfile(Map<String, Object> propertiesMap) ;

    private Map<String, Object> createOrGetMap(ConfigurableEnvironment environment) {
        PropertySource<?> propertySource = environment.getPropertySources().get(DEFAULT_PROPERTIES);
        if (propertySource != null) {
            return (Map<String, Object>) propertySource.getSource();
        }
        synchronized (this) {
            propertySource = environment.getPropertySources().get(DEFAULT_PROPERTIES);
            if (propertySource == null) {
                propertySource = new MapPropertySource(DEFAULT_PROPERTIES, new ConcurrentHashMap<>());
                environment.getPropertySources().addLast(propertySource);
            }
        }
        return (Map<String, Object>) propertySource.getSource();
    }


}
