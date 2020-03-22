package cn.ishow.kafka.init;
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
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.core.annotation.Order;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yinchong
 * @create 2019/11/11 21:37
 * @description
 */
@Order(102)
public class DefaultPropertiesInitialize extends AbstractEnvPropertiesInitialize {
    @Override
    public Map<String, Object> loadDefaultProperties(String env) {
        switch (env) {
            case "dev":
                return loadDevProperties();
            case "test":
                return loadTestProperties();
            case "prd":
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
        Map<String, Object> propertiesMap = new HashMap<>();
        propertiesMap.put("spring.kafka.bootstrap-servers", "127.0.0.1:9092");
        //消费端
        propertiesMap.put("spring.kafka.consumer.group-id", "dev");
        propertiesMap.put("spring.kafka.consumer.key-deserializer", StringDeserializer.class.getName());
        propertiesMap.put("spring.kafka.consumer.value-deserializer", StringDeserializer.class.getName());
        propertiesMap.put("spring.kafka.consumer.enable-auto-commit", "true");
        propertiesMap.put("spring.kafka.consumer.auto-commit-interval", "1000");
        propertiesMap.put("spring.kafka.consumer.auto-offset-reset", "latest");
        //生成端
        propertiesMap.put("spring.kafka.producer.acks", "1");
        propertiesMap.put("spring.kafka.producer.retries", "3");
        propertiesMap.put("spring.kafka.producer.key-serializer", StringSerializer.class.getName());
        propertiesMap.put("spring.kafka.producer.value-serializer", StringSerializer.class.getName());
        return propertiesMap;
    }
}
