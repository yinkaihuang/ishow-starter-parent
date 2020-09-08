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

import cn.ishow.common.inject.AbstractPropertiesInject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import java.util.Map;

/**
 * @author yinchong
 * @create 2019/11/24 10:16
 * @description
 */
@Slf4j
@Order(Integer.MIN_VALUE)
public class ApolloDefaultPropertiesInject extends AbstractPropertiesInject {

    @Override
    public void onDevProfile(Map<String, Object> propertiesMap) {
        propertiesMap.put("apollo.meta","http://localhost:8080");
    }

    @Override
    public void onStgProfile(Map<String, Object> propertiesMap) {

    }

    @Override
    public void onPrdProfile(Map<String, Object> propertiesMap) {

    }

    @Override
    public void onAllProfile(Map<String, Object> propertiesMap) {
        propertiesMap.put("apollo.bootstrap.enabled", "true");
    }
}
