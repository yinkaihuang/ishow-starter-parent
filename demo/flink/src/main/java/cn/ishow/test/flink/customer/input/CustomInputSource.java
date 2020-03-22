package cn.ishow.test.flink.customer.input;
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

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

/**
 * @author yinchong
 * @create 2019/11/19 21:40
 * @description
 */
public class CustomInputSource extends RichSourceFunction<String> {
    @Override
    public void run(SourceContext<String> sourceContext) throws Exception {
        for(int i=0;i<100;i++) {
            Thread.sleep(1000);
            sourceContext.collect("hello"+i);
        }
    }

    @Override
    public void cancel() {

    }

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
    }

    @Override
    public void close() throws Exception {
        super.close();
    }
}
