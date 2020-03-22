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

import cn.ishow.test.flink.es.FlinkESDemo;
import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer;
import org.apache.flink.streaming.connectors.elasticsearch6.ElasticsearchSink;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.Collector;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author yinchong
 * @create 2019/11/30 17:37
 * @description
 */
public class KafkaToEsDemo {
    private static Logger logger = LoggerFactory.getLogger(FlinkESDemo.class);

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        //构建kafka输入源的基础信息
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("group.id", "test-consumer-group");
        FlinkKafkaConsumer<String> consumer = new FlinkKafkaConsumer<>("flink-test-demo", new SimpleStringSchema(), properties);
        DataStreamSource<String> stream = env.addSource(consumer);
        //中间操作

        SingleOutputStreamOperator<String> apply = stream.map(new MapFunction<String, VisitorVO>() {
            @Override
            public VisitorVO map(String value) throws Exception {
                String[] datas = value.split("\t");
                String level = datas[2];
                String time = datas[3];
                String ip = datas[4];
                String domain = datas[5];
                Long traffic = Long.parseLong(datas[6]);

                VisitorVO build = VisitorVO.builder()
                        .domain(domain)
                        .traffic(traffic)
                        .createTime(getTime(time))
                        .build();
                return build;
            }
        }).assignTimestampsAndWatermarks(new AssignerWithPeriodicWatermarks<VisitorVO>() {

            private final long maxOutOfOrderness = 3500; // 3.5 seconds

            private long currentMaxTimestamp;

            @Override
            public long extractTimestamp(VisitorVO element, long previousElementTimestamp) {
                long timestamp = element.getCreateTime();
                currentMaxTimestamp = Math.max(timestamp, currentMaxTimestamp);
                return timestamp;
            }

            @Override
            public Watermark getCurrentWatermark() {
                // return the watermark as current highest timestamp minus the out-of-orderness bound
                return new Watermark(currentMaxTimestamp - maxOutOfOrderness);
            }
        }).keyBy(new KeySelector<VisitorVO, String>() {
            @Override
            public String getKey(VisitorVO value) throws Exception {
                return value.domain;
            }
        }).window(TumblingEventTimeWindows.of(Time.seconds(60)))
                .apply(new WindowFunction<VisitorVO, String, String, TimeWindow>() {
                    @Override
                    public void apply(String s, TimeWindow window, Iterable<VisitorVO> input, Collector<String> out) throws Exception {
                        String domain = s;
                        long sum = 0L;
                        long time = 0L;
                        Iterator<VisitorVO> iterator = input.iterator();
                        while (iterator.hasNext()) {
                            VisitorVO next = iterator.next();
                            Long traffic = next.traffic;
                            sum += traffic;
                            if (time == 0) {
                                time = next.createTime;
                            }
                        }
                        Map<String, Object> result = new HashMap<>();
                        result.put("domain", domain);
                        result.put("totalTraffic", sum);
                        result.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(time)));
                        String content = JSON.toJSONString(result);
                        out.collect(content);
                    }
                });


        //存储地方
        List<HttpHost> addressList = new ArrayList<>();
        addressList.add(new HttpHost("localhost", 9200, "http"));

        ElasticsearchSink.Builder builder = new ElasticsearchSink.Builder(addressList, new ElasticsearchSinkFunction<String>() {
            @Override
            public void process(String message, RuntimeContext runtimeContext, RequestIndexer indexer) {
                logger.info("process message:{}", message);
                indexer.add(createIndexRequest(message));
            }

            public IndexRequest createIndexRequest(String message) {
                Map body = JSON.parseObject(message, Map.class);
                return Requests.indexRequest()
                        .index(createIndex("flink-test-demo"))
                        .type("_doc")
                        .source(body);
            }
        });
        builder.setBulkFlushMaxActions(1);
        apply.addSink(builder.build());
        env.execute("flink and es job run");

    }

    private static String createIndex(String prefixIndex) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String suffixIndex = dateFormat.format(new Date());
        return prefixIndex + "-" + suffixIndex;
    }

    private static long getTime(String dataStr) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return format.parse(dataStr).getTime();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    static class VisitorVO {
        private String domain;
        private Long traffic;
        private Long createTime;
    }
}
