package cn.ishow.jdbc.initialize;
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
 * @create 2019/11/11 18:26
 * @description
 */
@Slf4j
@Order(100)
public class DefaultPropertiesInitialize extends AbstractEnvPropertiesInitialize {


    @Override
    public Map<String, Object> loadDefaultProperties(String env) {
        switch (env) {
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

    private Map<String, Object> loadDevProperties() {
        Map<String, Object> source = new HashMap<>();
        source.put("spring.datasource.type", "com.alibaba.druid.pool.DruidDataSource");
        source.put("spring.datasource.driver-class-name", "com.mysql.cj.jdbc.Driver");
        source.put("spring.datasource.druid.initial-size", "5");
        source.put("spring.datasource.druid.min-idle", "5");
        source.put("spring.datasource.druid.maxActive", "20");
        source.put("spring.datasource.druid.maxWait", "60000");
        source.put("spring.datasource.druid.timeBetweenEvictionRunsMillis", "60000");
        source.put("spring.datasource.druid.minEvictableIdleTimeMillis", "300000");
        source.put("spring.datasource.druid.validationQuery", "SELECT 1");
        source.put("spring.datasource.druid.testWhileIdle", "true");
        source.put("spring.datasource.druid.testOnBorrow", "false");
        source.put("spring.datasource.druid.testOnReturn", "false");
        source.put("spring.datasource.druid.poolPreparedStatements", "true");
        source.put("spring.datasource.druid.maxPoolPreparedStatementPerConnectionSize", "20");
        source.put("spring.datasource.druid.filters", "stat,wall");
        source.put("spring.datasource.druid.connectionProperties", "druid.stat.mergeSql=true;druid.stat.slowSql");
        source.put("spring.datasource.druid.stat-view-servlet.login-username", "root");
        source.put("spring.datasource.druid.stat-view-servlet.login-password", "root");
        return source;
    }
}
