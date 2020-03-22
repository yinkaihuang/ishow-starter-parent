package cn.ishow.test.flink;
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

import lombok.Data;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.LocalEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yinchong
 * @create 2019/11/12 8:33
 * @description
 */
public class WordCountDemo {
    public static void main(String[] args) throws Exception {
        LocalEnvironment env = ExecutionEnvironment.createLocalEnvironment();
        DataSource<String> dataSource = env.readTextFile("E://flink//in.txt");
        AggregateOperator<Tuple2<String,Integer>> wordCount = dataSource.flatMap(new LineSplitter()).groupBy(0).sum(1);
        wordCount.print();
        env.execute("flink world count demo");
    }

    public static class LineSplitter implements FlatMapFunction<String, Tuple2<String,Integer>> {

        @Override
        public void flatMap(String line, Collector<Tuple2<String,Integer>> out) {
            for (String word : line.split(",")) {
                out.collect(new Tuple2<String,Integer>(word,1));
            }
        }
    }
}
