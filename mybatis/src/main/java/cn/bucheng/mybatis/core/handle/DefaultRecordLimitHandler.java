package cn.bucheng.mybatis.core.handle;
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

import cn.bucheng.mybatis.core.exception.ToManyRecordException;
import org.apache.ibatis.executor.result.DefaultResultContext;

import java.util.List;

/**
 * @author yinchong
 * @create 2020/3/22 10:44
 * @description
 */
public class DefaultRecordLimitHandler {
    public static volatile int defaultSize = 1000;

    public static void changeDefaultSize(int defaultSize) {
        DefaultRecordLimitHandler.defaultSize = defaultSize;
    }


    public static void handleRecord(DefaultResultContext<Object> context) {
        if (context == null || context.getResultCount() <= defaultSize) {
            return;
        }
        int reallySize = context.getResultCount();
        throw new ToManyRecordException("当前查询出来记录数 为：" + reallySize + " 大于默认配置数量：" + defaultSize);
    }
}
