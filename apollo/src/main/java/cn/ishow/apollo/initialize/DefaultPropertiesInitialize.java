package cn.ishow.apollo.initialize;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yinchong
 * @create 2019/11/24 10:16
 * @description
 */
@Slf4j
@Order(-Integer.MAX_VALUE)
public class DefaultPropertiesInitialize extends AbstractEnvPropertiesInitialize {

    @Override
    public Map<String, Object> loadDefaultProperties(String env) {
        Map<String, Object> result = new HashMap<>();
        loadCommonProperites(result);
        switch (env){
            case EnvironmentConstant.DEV:
                loadDEVProperties(result);
                break;
            case EnvironmentConstant.PRD:
                break;
            case EnvironmentConstant.TEST:
                break;
            default:
              log.error("error env {}",env);
        }
        return result;
    }

    private void loadCommonProperites(Map<String, Object> map) {
        map.put("apollo.bootstrap.enabled", "true");
    }

    private void loadDEVProperties(Map<String,Object> map){
        map.put("apollo.meta","http://localhost:8080");
    }
}
