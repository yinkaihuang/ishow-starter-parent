package cn.ishow.common.model.vo;
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

import cn.ishow.common.exception.BizRuntimeException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author yinchong
 * @create 2019/11/11 17:36
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVO implements Serializable {
    private int code;
    private String message;
    private Object data;

    public static ResultVO success(Object data, String message) {
        return new ResultVO(200, message, data);
    }

    public static ResultVO success(Object data) {
        return success(data, "执行成功");
    }

    public static ResultVO success() {
        return successWithMsg("执行成功");
    }

    public static ResultVO successWithMsg(String message) {
        return success(null, message);
    }

    public static ResultVO fail(int code, String message) {
        return new ResultVO(code, message, null);
    }

    public static ResultVO fail(BizRuntimeException error) {
        return fail(error.getCode(), error.getMessage());
    }

    public static ResultVO error(String message, String detail) {
        return new ResultVO(-1, message, detail);
    }
}
