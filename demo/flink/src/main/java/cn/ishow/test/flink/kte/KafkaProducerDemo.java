package cn.ishow.test.flink.kte;
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

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.StringSerializer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

/**
 * @author yinchong
 * @create 2019/11/30 18:25
 * @description
 */
public class KafkaProducerDemo {
    public static void main(String[] args) throws InterruptedException {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        properties.put(ProducerConfig.ACKS_CONFIG,"0");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        KafkaProducer<String,String> producer = new KafkaProducer<String, String>(properties);
        String topic = "";
        for(;;){
            StringBuilder sb = new StringBuilder();
            sb.append("immooc").append("\t")
                    .append("CN").append("\t")
                    .append(createLevel()).append("\t")
                    .append(createTime()).append("\t")
                    .append(createIp()).append("\t")
                    .append(createDomain()).append("\t")
                    .append(createTraffic()).append("\t");


            producer.send(new ProducerRecord<>(topic,sb.toString()));
            Thread.sleep(2000);
        }

    }

    private static String createTime(){
        return new SimpleDateFormat("yyy-MM-dd HH:mm:ss").format(new Date());
    }

    private static String  createIp(){
        String[] ips =new String[]{"192.168.3.44","192.145.45.78","172.45.23.56","167.34.35.33"};
        return ips[new Random().nextInt(ips.length)];
    }

    private static String createLevel(){
        String[] levels = new String[]{"M","E"};
        return levels[new Random().nextInt(levels.length)];
    }

    private static String createDomain(){
        String[] domains = new String[]{"www.baidu.com","www.taobao.com","www.hao123.com","www.test.com"};
        return domains[new Random().nextInt(domains.length)];
    }

    private static long createTraffic(){
        return new Random().nextInt(1000);
    }
}
