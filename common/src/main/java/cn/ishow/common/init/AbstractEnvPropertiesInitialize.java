package cn.ishow.common.init;
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import cn.ishow.common.constant.EnvironmentConstant;
import cn.ishow.common.ready.SpringBootReady;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yinchong
 * @create 2019/11/11 21:02
 * @description
 */
@Order(Integer.MAX_VALUE)
public abstract class AbstractEnvPropertiesInitialize implements EnvironmentPostProcessor {
    public static final String DEFAULT_PROPERTIES = "default-properties";
    private ConfigurableEnvironment environment;

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        if (!SpringBootReady.isReady()) {
            return;
        }
        String active = environment.getProperty("spring.profile.actives");
        if (active == null || active.equals("")) {
            active = EnvironmentConstant.DEV;
        }
        Map<String, Object> defaultProperties = loadDefaultProperties(active);
        Map<String, Object> propertiesMap = getAndInitSource(environment);
        propertiesMap.putAll(defaultProperties);
    }

    private Map<String, Object> getAndInitSource(ConfigurableEnvironment environment) {
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

    public abstract Map<String, Object> loadDefaultProperties(String env);


}
