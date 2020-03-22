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

import lombok.Data;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import javax.sql.DataSource;
import java.util.Random;

/**
 * @author yinchong
 * @create 2019/11/23 11:56
 * @description
 */
public class StudentOperationTest {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        SourceFunction<Student> source = new InStream();
        DataStreamSource<Student> stream = env.addSource(source);

        stream.flatMap(new FlatMapFunction<Student, Student>() {
            @Override
            public void flatMap(Student student, Collector<Student> collector) throws Exception {
                if (student.getAge() < 90) {
                    collector.collect(student);
                }
            }
        }).keyBy(new KeySelector<Student, String>() {
            @Override
            public String getKey(Student student) throws Exception {
                return student.getGender();
            }
        }).reduce(new ReduceFunction<Student>() {
            @Override
            public Student reduce(Student t1, Student t2) throws Exception {
                Student student = new Student();
                student.setName("总年龄");
                student.setGender(t1.getGender());
                student.setCount(t1.getCount()+t2.getCount());
                student.setAge(t1.getAge() + t2.getAge());
                return student;
            }
        }).print();

        env.execute("student gender avg age");
    }

    @Data
    static class Student {
        private String name;
        private Integer age;
        private String gender;
        private Integer count;
    }

    public static class InStream implements SourceFunction<Student> {
        volatile boolean stop = false;

        @Override
        public void run(SourceContext<Student> ctx) throws Exception {
            Random random = new Random();
            while (!stop) {
                Student student = new Student();
                int index = random.nextInt(100);
                if (index % 2 == 0) {
                    student.setGender("男");
                    student.setName("尹冲(" + index + ")");
                } else {
                    student.setGender("女");
                    student.setName("余翠(" + index + ")");
                }
                student.setCount(1);
                student.setAge(index);
                ctx.collect(student);
            }
        }

        @Override
        public void cancel() {
            stop = true;
        }
    }

    static class OutStream implements SinkFunction<Student> {
        @Override
        public void invoke(Student value, Context context) throws Exception {
            System.out.println("===>" + value);
        }
    }
}
