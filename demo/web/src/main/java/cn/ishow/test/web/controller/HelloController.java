package cn.ishow.test.web.controller;
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

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yinchong
 * @create 2019/11/11 20:56
 * @description
 */
@RestController
@Slf4j
public class HelloController {


    @RequestMapping("hello")
    public String hello() {
        log.info("info");
        log.warn("warn");
        log.error("error");
        return "hello";
    }

    @RequestMapping("updateLevel")
    public String updateLeveL(String level) {
        org.apache.logging.log4j.core.LoggerContext ctx = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
        Configuration config = ctx.getConfiguration();
        LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
        level = level.toLowerCase();
        String lastLevel = loggerConfig.getLevel().name().toLowerCase();
        if (lastLevel.equals(level)) {
            return "no";
        }
        switch (level) {
            case "trace":
                loggerConfig.setLevel(org.apache.logging.log4j.Level.TRACE);
                break;
            case "debug":
                loggerConfig.setLevel(org.apache.logging.log4j.Level.DEBUG);
                break;
            case "info":
                loggerConfig.setLevel(org.apache.logging.log4j.Level.INFO);
                break;
            case "warn":
                loggerConfig.setLevel(org.apache.logging.log4j.Level.WARN);
                break;
            case "error":
                loggerConfig.setLevel(org.apache.logging.log4j.Level.ERROR);
                break;
            default:
                log.error("logger level edit fail,日志级别修改失败！");
                return "error";
        }
        ctx.updateLoggers();
        log.info("logger level update success ,from {} to {}", lastLevel, level);
        return "success";
    }


    @RequestMapping("test")
    public String test(){
        return "test";
    }
}
