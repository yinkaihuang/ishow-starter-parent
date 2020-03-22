package cn.ishow.common.ready;
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

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;

/**
 * @author yinchong
 * @create 2019/11/11 18:29
 * @description
 */
@Order(Integer.MAX_VALUE)
public class SpringBootReady implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    private static volatile boolean ready = false;

    static {
        ready = !isCloud();
    }

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent applicationEvent) {
        ready = true;
    }

    public static boolean isReady() {
        return ready;
    }

    private static boolean isCloud() {
        try {
            Class.forName("org.springframework.cloud.bootstrap.BootstrapApplicationListener");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
