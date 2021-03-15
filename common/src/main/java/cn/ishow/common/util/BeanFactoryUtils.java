package cn.ishow.common.util;
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

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.util.Map;

/**
 * @author yinchong
 * @create 2019/11/23 21:50
 * @description
 */
public class BeanFactoryUtils implements BeanFactoryPostProcessor {
    private static DefaultListableBeanFactory beanFactory;

    public static <T> T getBean(Class<T> clazz){
        return beanFactory.getBean(clazz);
    }

    public static <T> T getBean(String name,Class<T> clazz){
        return beanFactory.getBean(name,clazz);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanFactoryUtils.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }


    public static Map<String,Object>  listBeanWithAnnotation(Class annotation){
        return beanFactory.getBeansWithAnnotation(annotation);
    }
}
