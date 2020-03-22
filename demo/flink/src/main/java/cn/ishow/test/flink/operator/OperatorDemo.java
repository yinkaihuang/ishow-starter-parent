package cn.ishow.test.flink.operator;
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

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;
import org.apache.flink.util.TimeUtils;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author yinchong
 * @create 2019/11/20 8:40
 * @description
 */
public class OperatorDemo {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        SourceFunction<String> source = new InputSource();
        DataStreamSource<String> stream = env.addSource(source);
        stream.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
                collector.collect(new Tuple2<>(s, 1));
            }
        }).keyBy(0).countWindow(1000).sum(1).print();

        env.execute("test flink job operator");

    }

    static class InputSource extends RichSourceFunction<String> {

        private volatile boolean stop = false;

        @Override
        public void run(SourceContext<String> sourceContext) throws Exception {
            for (; ; ) {
                if (stop) {
                    return;
                }
                Random random = new Random();
                String value = "test" + random.nextInt(10);
                sourceContext.collect(value);
                Thread.sleep(1000);
            }
        }

        @Override
        public void cancel() {
            stop = true;
        }
    }
}
