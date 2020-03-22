package cn.ishow.common.util;
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

import com.google.common.base.Strings;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

/**
 * @author yinchong
 * @create 2019/11/23 21:53
 * @description
 */
@Order(-Integer.MAX_VALUE)
public class EnvironmentUtils implements EnvironmentPostProcessor {
    private static ConfigurableEnvironment environment;
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        EnvironmentUtils.environment =environment;
    }

    public static String getValue(String key){
        return getValue(key,null);
    }

    public static String getValue(String key,String defaultValue){
       String value = environment.getProperty(key);
       if(Strings.isNullOrEmpty(value)){
           return defaultValue;
       }
       return value;
    }
}
