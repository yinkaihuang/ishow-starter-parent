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

import com.sun.javaws.progress.Progress;
import lombok.Data;
import lombok.ToString;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author yinchong
 * @create 2019/11/20 8:59
 * @description
 */
public class TraceOperationDemo {

    public static void main(String[] args) throws Exception {
//        traceFlink();

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        SourceFunction<TraceVO> source = new InputSource();
        DataStreamSource<TraceVO> stream = env.addSource(source);
        stream.keyBy(new KeySelector<TraceVO, String>() {
            @Override
            public String getKey(TraceVO traceVO) throws Exception {
                return traceVO.getTraceId();
            }
        }).timeWindow(Time.seconds(60))
                //参数一 ：入参  参数二： 出参 参数三：采用统计的参数
                .apply(new WindowFunction<TraceVO, List<TraceVO>, String, TimeWindow>() {
                    @Override
                    public void apply(String s, TimeWindow timeWindow, Iterable<TraceVO> iterable, Collector<List<TraceVO>> collector) throws Exception {
                        List<TraceVO> traceVOList = new LinkedList<>();
                        Iterator<TraceVO> iterator = iterable.iterator();
                        while (iterator.hasNext()) {
                            TraceVO next = iterator.next();
                            traceVOList.add(next);
                        }
                        collector.collect(traceVOList);
                    }
                })
                .flatMap(new FlatMapFunction<List<TraceVO>, List<TraceVO>>() {
                    private List<String> keyList = new LinkedList<>();
                    @Override
                    public void flatMap(List<TraceVO> value, Collector<List<TraceVO>> out) throws Exception {
                        StringBuilder sb = new StringBuilder();
                        for(TraceVO vo:value){
                            sb.append(vo.getPService())
                                    .append("-")
                                    .append(vo.getServiceName())
                                    .append("-")
                                    .append(vo.getUrl());
                        }
                        String key = sb.toString();
                        if(!keyList.contains(key)){
                            out.collect(value);
                            return;
                        }
                        System.out.println("key:"+key+" exist");
                    }
                })
                .print();

        env.execute("flink test job");
    }

    private static void traceFlink() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        SourceFunction<TraceVO> source = new InputSource();
        DataStreamSource<TraceVO> stream = env.addSource(source);
        stream.keyBy(new KeySelector<TraceVO, String>() {
            @Override
            public String getKey(TraceVO traceVO) throws Exception {
                return traceVO.getTraceId();
            }
        }).timeWindow(Time.seconds(60)).apply(new WindowFunction<TraceVO, List<TraceVO>, String, TimeWindow>() {
            @Override
            public void apply(String s, TimeWindow timeWindow, Iterable<TraceVO> iterable, Collector<List<TraceVO>> collector) throws Exception {
                List<TraceVO> traceVOList = new LinkedList<>();
                Iterator<TraceVO> iterator = iterable.iterator();
                while (iterator.hasNext()) {
                    TraceVO next = iterator.next();
                    traceVOList.add(next);
                }
                collector.collect(traceVOList);
            }
        }).print();

        env.execute("flink test job");
    }

    @Data
    @ToString
    static class TraceVO implements Serializable {
        private String traceId;
        private String pService;
        private String serviceName;
        private String url;
    }

    static class InputSource extends RichSourceFunction<TraceVO> {
        private volatile boolean stop = false;

        @Override
        public void run(SourceContext<TraceVO> sourceContext) throws Exception {
            Random random = new Random();
            int index = 0;
            for (; ; ) {
                if (stop)
                    return;
                int number = random.nextInt(2);
                String traceId = "xdi35df2409kdsd13fd" + (index++);
                sourceContext.collect(createRoot(traceId, number));
                sourceContext.collect(createSMS(traceId, number));
                sourceContext.collect(createPay(traceId, number));
                Thread.sleep(10);
            }
        }

        @Override
        public void cancel() {
            stop = true;
        }
    }

    private static TraceVO createRoot(String traceId, int i) {
        TraceVO traceVO = new TraceVO();
        traceVO.setTraceId(traceId);
        traceVO.setPService(null);
        traceVO.setServiceName("api-gateway");
        traceVO.setUrl("/api-gateway/sms/detail" + i);
        return traceVO;
    }

    private static TraceVO createSMS(String traceId, int i) {
        TraceVO traceVO = new TraceVO();
        traceVO.setPService("api-gateway");
        traceVO.setServiceName("sms");
        traceVO.setTraceId(traceId);
        traceVO.setUrl("/sms/detail" + i);
        return traceVO;
    }

    private static TraceVO createPay(String traceId, int i) {
        TraceVO traceVO = new TraceVO();
        traceVO.setPService("sms");
        traceVO.setServiceName("pay");
        traceVO.setTraceId(traceId);
        traceVO.setUrl("/pay/order" + i);
        return traceVO;
    }
}
