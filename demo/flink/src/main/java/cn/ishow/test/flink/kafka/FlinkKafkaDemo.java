package cn.ishow.test.flink.kafka;
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

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @author yinchong
 * @create 2019/11/15 22:10
 * @description
 */
public class FlinkKafkaDemo {
    public static void main(String[] args)throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //构建kafka输入源的基础信息
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("group.id", "test-consumer-group");
        FlinkKafkaConsumer<String> consumer = new FlinkKafkaConsumer<>("hello", new SimpleStringSchema(), properties);
       //添加数据源为kafka
        DataStream<String> stream = env.addSource(consumer);
        stream.print();

        //构造kafka输出源的基础信息
        FlinkKafkaProducer producer = new FlinkKafkaProducer("localhost:9092",
                "flink-demo",
                new SimpleStringSchema());

        //将结果写到kafka
        stream.addSink(producer).name("flink-connectors-kafka");

        env.execute("write to kafka");
    }
}
