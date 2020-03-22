package cn.bucheng.mybatis.core.loader;
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

import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;

/**
 * @author yinchong
 * @create 2020/3/22 11:28
 * @description
 */
public class SpringClassReLoader {

    /** org.apache.ibatis.executor.resultset.DefaultResultSetHandler
     * 重新加载，类对象到内存中
     * @param bytes
     */
    public static void reload(String name,byte[] bytes){
        ClassLoader loader = ClassUtils.getDefaultClassLoader();
        try {
            Method defineClass = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class);
            defineClass.setAccessible(true);
            defineClass.invoke(loader,name,bytes,0,bytes.length);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
