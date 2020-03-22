package cn.ishow.web.initialize;
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

import cn.ishow.common.init.AbstractEnvPropertiesInitialize;
import org.springframework.core.annotation.Order;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yinchong
 * @create 2019/11/11 21:00
 * @description
 */
@Order(Integer.MAX_VALUE)
public class DefaultEnvPropertiesInitialize extends AbstractEnvPropertiesInitialize {

    @Override
    public Map<String, Object> loadDefaultProperties(String env) {
        Map<String, Object> propertiesMap = new HashMap<>();
        propertiesMap.put("spring.mvc.throw-exception-if-no-handler-found", true);
        propertiesMap.put("spring.resources.add-mappings", false);
        propertiesMap.put("spring.http.encoding.charset", "utf-8");
        propertiesMap.put("spring.http.encoding.force", true);
        propertiesMap.put("spring.http.encoding.enabled", true);
        return propertiesMap;
    }
}
