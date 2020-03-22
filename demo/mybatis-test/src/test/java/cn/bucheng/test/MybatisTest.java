package cn.bucheng.test;
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

import cn.bucheng.demo.MybatisApplication;
import cn.bucheng.demo.domain.Order;
import cn.bucheng.demo.mapper.OrderMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author yinchong
 * @create 2020/3/22 14:06
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybatisApplication.class)
public class MybatisTest {
    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void testSave() {
        for (int i = 0; i < 5000; i++) {
            Order order = Order.builder().id((long) i).orderId((long) i).detail("this is test for order").build();
            orderMapper.save(order);
        }
    }

    @Test
    public void listAll(){
        List<Order> orders = orderMapper.listAll();
        System.out.println(orders.size());
    }

}
