package cn.ishow.test.kafka.producer;
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

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author yinchong
 * @create 2019/11/11 22:07
 * @description
 */
@Slf4j
@Component
public class KafkaProducer implements CommandLineRunner {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void run(String... args) throws Exception {
        startSend();
    }

    private void startSend() {
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                Student student = new Student();
                student.setAge(i);
                student.setGender("男" + i);
                student.setName("尹步程" + i);
                kafkaTemplate.send("hello", JSON.toJSONString(student));
                log.info("send message success, i:{}", i);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    @Data
    static class Student implements Serializable {
        private String name;
        private Integer age;
        private String gender;
    }
}
