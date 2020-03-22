package cn.ishow.common.exception;
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
import lombok.Data;

/**
 * @author yinchong
 * @create 2019/11/11 17:42
 * @description
 */
@Data
public class BizRuntimeException extends RuntimeException {
    private int code;
    private String message;

    public BizRuntimeException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BizRuntimeException(BusinessError error) {
        this.code = error.getCode();
        this.message = error.getMessage();
    }

    public BizRuntimeException(BusinessError error, String message) {
        this.code = error.getCode();
        this.message = message;
    }

}
