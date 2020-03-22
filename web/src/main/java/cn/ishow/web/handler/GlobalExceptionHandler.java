package cn.ishow.web.handler;
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

import cn.ishow.common.enu.BusinessError;
import cn.ishow.common.exception.BizRuntimeException;
import cn.ishow.common.model.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;

/**
 * @author yinchong
 * @create 2019/11/11 18:04
 * @description
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {Exception.class, Throwable.class})
    @ResponseBody
    public Object handleError(HttpServletRequest req, HttpServletResponse resp, Object error) {

        if (error instanceof BizRuntimeException) {
            BizRuntimeException bus = (BizRuntimeException) error;
            log.error("BizRuntimeException code:{} detail:{}",bus.getCode(),bus.getMessage());
            return ResultVO.fail(bus.getCode(), bus.getMessage());
        } else if (error instanceof ServletRequestBindingException) {
            log.error("ServletRequestBindingException detail:{}",error.toString());
            return ResultVO.fail(BusinessError.BIND_PARAM_FAIL.getCode(), BusinessError.BIND_PARAM_FAIL.getMessage());
        } else if (error instanceof NoHandlerFoundException) {
            log.error("NoHandlerFoundException detail:{}",error.toString());
            return ResultVO.fail(BusinessError.NO_FIND_ROUTING.getCode(), BusinessError.NO_FIND_ROUTING.getMessage());
        } else if (error instanceof MethodArgumentNotValidException) {
            log.error("MethodArgumentNotValidException detail:{}",error.toString());
            MethodArgumentNotValidException mse = (MethodArgumentNotValidException) error;
            return ResultVO.fail(BusinessError.PARAM_VERIFY_FAIL.getCode(), BusinessError.PARAM_VERIFY_FAIL.getMessage() + ":" + errorMessage(mse.getBindingResult()));
        }else if(error instanceof BindException){
            BindException bindException = (BindException)error;
            return ResultVO.fail(BusinessError.PARAM_VERIFY_FAIL.getCode(),errorMessage(bindException.getBindingResult())+"");
        } else {
            log.error(" detail:{}",error.toString());
            return ResultVO.error("服务器异常", error.toString());
        }
    }


    private List<String> errorMessage(BindingResult result){
        List<String> errors = new LinkedList<>();
        List<ObjectError> allErrors = result.getAllErrors();
        if(allErrors!=null){
            for(ObjectError error:allErrors){
                errors.add(error.getDefaultMessage());
            }
        }

        return errors;
    }
}
