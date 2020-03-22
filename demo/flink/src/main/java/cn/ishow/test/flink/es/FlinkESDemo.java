package cn.ishow.test.flink.es;
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
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.LocalEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer;
import org.apache.flink.streaming.connectors.elasticsearch6.ElasticsearchSink;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author yinchong
 * @create 2019/11/15 22:26
 * @description
 */
public class FlinkESDemo {
    private static Logger logger = LoggerFactory.getLogger(FlinkESDemo.class);

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //构建kafka输入源的基础信息
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("group.id", "test-consumer-group");
        FlinkKafkaConsumer<String> consumer = new FlinkKafkaConsumer<>("hello", new SimpleStringSchema(), properties);

        DataStreamSource<String> stream = env.addSource(consumer);

        //中间操作


        //存储地方
        List<HttpHost> addressList = new ArrayList<>();
        addressList.add(new HttpHost("localhost",9200,"http"));

        ElasticsearchSink.Builder builder = new ElasticsearchSink.Builder(addressList, new ElasticsearchSinkFunction<String>() {
            @Override
            public void process(String message, RuntimeContext runtimeContext, RequestIndexer indexer) {
                logger.info("process message:{}",message);
                indexer.add(createIndexRequest(message));
            }

            public IndexRequest createIndexRequest(String message) {
                Map body = JSON.parseObject(message, Map.class);
                return Requests.indexRequest()
                        .index(createIndex("flink-demo"))
                        .type("_doc")
                        .source(body);
            }
        });
        builder.setBulkFlushMaxActions(1);

        stream.addSink(builder.build());

        env.execute("flink and es job run");

    }

    private static String createIndex(String prefixIndex) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String suffixIndex = dateFormat.format(new Date());
        return prefixIndex + "-" + suffixIndex;
    }
}
