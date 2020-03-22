package cn.ishow.test.flink.operator.conect;
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

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.CoFlatMapFunction;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.apache.flink.util.Collector;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author yinchong
 * @create 2019/11/30 21:04
 * @description
 */
public class DataSourceConnectDemo {

    public static void main(String[] args)throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        DataStreamSource<String> streamOne = env.addSource(new InputSourceOne());

        DataStreamSource<Integer> streamTwo = env.addSource(new InputSourceTwo());

        streamOne.connect(streamTwo).flatMap(new CoFlatMapFunction<String, Integer, Map<String, Object>>() {

            Integer number;

            @Override
            public void flatMap1(String value, Collector<Map<String, Object>> out) throws Exception {
                Map<String, Object> map = new HashMap<>();
                map.put("content", value);
                map.put("number", number);
                out.collect(map);
            }

            @Override
            public void flatMap2(Integer value, Collector<Map<String, Object>> out) throws Exception {
                this.number = value;
            }
        }).print();

        env.execute("test job");
    }

    static class InputSourceOne extends RichSourceFunction<String> {
        volatile boolean stop = false;

        @Override
        public void run(SourceContext<String> ctx) throws Exception {
            for (; ; ) {
                if (stop) {
                    break;
                }
                ctx.collect("hello word" + new Random().nextInt(100));
                Thread.sleep(100);
            }
        }

        @Override
        public void cancel() {
            stop = true;
        }
    }

    static class InputSourceTwo extends RichSourceFunction<Integer> {
        volatile boolean stop = false;

        @Override
        public void run(SourceContext<Integer> ctx) throws Exception {
            for (; ; ) {
                if (stop)
                    break;
                ctx.collect(new Random().nextInt(1000));
                Thread.sleep(1000);
            }
        }

        @Override
        public void cancel() {
            stop = true;
        }
    }
}
