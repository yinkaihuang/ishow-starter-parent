package cn.bucheng.mybatis.core.env;
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

import cn.bucheng.mybatis.core.asm.MybatisMethodUtils;
import cn.bucheng.mybatis.core.loader.SpringClassReLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author yinchong
 * @create 2020/3/22 12:49
 * @description
 */
@Slf4j
public class MybatisLoaderInject implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        SpringClassReLoader.reload("org.apache.ibatis.executor.statement.PreparedStatementHandler",MybatisMethodUtils.flowHandleResult());
        SpringClassReLoader.reload("org.apache.ibatis.executor.resultset.DefaultResultSetHandler",MybatisMethodUtils.rowHandleInject());
        log.info("reload method success");
    }
}
