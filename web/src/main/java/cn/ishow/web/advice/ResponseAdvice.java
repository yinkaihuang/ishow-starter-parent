package cn.ishow.web.advice;
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

import cn.ishow.common.model.vo.ResultVO;
import cn.ishow.web.annotation.IgnoreAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author yinchong
 * @create 2019/11/11 17:54
 * @description
 */
@Slf4j
@ControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        IgnoreAdvice classAdvice = methodParameter.getDeclaringClass().getAnnotation(IgnoreAdvice.class);
        if (classAdvice != null) {
            IgnoreAdvice methodAdvice = methodParameter.getMethodAnnotation(IgnoreAdvice.class);
            if (methodAdvice != null) {
                return !methodAdvice.ignore();
            }
            return !classAdvice.ignore();
        }
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (o == null) {
            return ResultVO.success();
        } else if (o instanceof ResultVO) {
            return o;
        } else {
            return ResultVO.success(o);
        }
    }
}
