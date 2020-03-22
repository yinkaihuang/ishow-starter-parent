package cn.ishow.log;
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


import cn.ishow.common.util.EnvironmentUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.springframework.boot.CommandLineRunner;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author yinchong
 * @create 2019/11/23 21:27
 * @description
 */
@Slf4j
public class LoggerLevelUpdateTask implements CommandLineRunner {

    public static final String LOGGER_LEVEL = "logger.level";
    public static final String TRACE = "trace";
    public static final String DEBUG = "debug";
    public static final String INFO = "info";
    public static final String WARN = "warn";
    public static final String ERROR = "error";
    private LoggerConfig loggerConfig;
    private org.apache.logging.log4j.core.LoggerContext ctx;
    private Timer timer = new Timer("log-level-task");

    private static volatile String preLevel;

    public void updateLogLevel(String level) {
        if (Objects.equals(preLevel, level)) {
            log.debug("level:{} and lastLevel:{} is same", preLevel, level);
            return;
        }
        level = level.toLowerCase();
        initLogger();
        preLevel = loggerConfig.getLevel().name().toLowerCase();
        if (Objects.equals(preLevel, level)) {
            return;
        }
        switch (level) {
            case TRACE:
                loggerConfig.setLevel(org.apache.logging.log4j.Level.TRACE);
                break;
            case DEBUG:
                loggerConfig.setLevel(org.apache.logging.log4j.Level.DEBUG);
                break;
            case INFO:
                loggerConfig.setLevel(org.apache.logging.log4j.Level.INFO);
                break;
            case WARN:
                loggerConfig.setLevel(org.apache.logging.log4j.Level.WARN);
                break;
            case ERROR:
                loggerConfig.setLevel(org.apache.logging.log4j.Level.ERROR);
                break;
            default:
                log.error("logger level update to {} fail,日志级别修改失败！", level);
                return;
        }
        ctx.updateLoggers();
        log.info("logger level update success ,from {} to {}", preLevel, level);
    }

    private void initLogger() {
        if (loggerConfig == null) {
            ctx = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
            Configuration config = ctx.getConfiguration();
            loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                String level = EnvironmentUtils.getValue(LOGGER_LEVEL, "info");
                updateLogLevel(level);
            }
        }, 0, 10 * 1000);
    }
}
