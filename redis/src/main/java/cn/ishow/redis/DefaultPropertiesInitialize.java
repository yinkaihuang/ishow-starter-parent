package cn.ishow.redis;
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
import cn.ishow.common.init.AbstractEnvPropertiesInitialize;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yinchong
 * @create 2019/11/11 22:40
 * @description
 */
public class DefaultPropertiesInitialize extends AbstractEnvPropertiesInitialize {
    @Override
    public Map<String, Object> loadDefaultProperties(String env) {
        switch (env){
            case EnvironmentConstant.DEV:
                return loadDevProperties();
            case EnvironmentConstant.TEST:
                return loadTestProperties();
            case EnvironmentConstant.PRD:
                return loadPrdProperties();
        }
        return null;
    }

    private Map<String, Object> loadPrdProperties() {
        return null;
    }

    private Map<String, Object> loadTestProperties() {
        return null;
    }

    private Map<String,Object> loadDevProperties(){
        Map<String,Object> propertiesMap = new HashMap<>();
        propertiesMap.put("spring.redis.database",0);
        propertiesMap.put("spring.redis.host","127.0.0.1");
        propertiesMap.put("spring.redis.port",6379);
        propertiesMap.put("spring.redis.password","");
        propertiesMap.put("spring.redis.pool.max-active",200);
        propertiesMap.put("spring.redis.pool.max-wait",-1);
        propertiesMap.put("spring.redis.pool.max-idle",10);
        propertiesMap.put("spring.redis.pool.min-idle",5);
        propertiesMap.put("spring.redis.timeout",10000);
        return propertiesMap;
    }
}
