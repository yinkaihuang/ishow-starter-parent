package cn.ishow.jdbc.initialize;


import cn.ishow.common.inject.AbstractPropertiesInject;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author yinchong
 * @create 2019/11/11 18:26
 * @description
 */
@Slf4j
public class JDBCDefaultPropertiesInject extends AbstractPropertiesInject {


    @Override
    public void onDevProfile(Map<String, Object> propertiesMap) {
        propertiesMap.put("spring.datasource.type", "com.alibaba.druid.pool.DruidDataSource");
        propertiesMap.put("spring.datasource.driver-class-name", "com.mysql.cj.jdbc.Driver");
        propertiesMap.put("spring.datasource.druid.initial-size", "5");
        propertiesMap.put("spring.datasource.druid.min-idle", "5");
        propertiesMap.put("spring.datasource.druid.maxActive", "20");
        propertiesMap.put("spring.datasource.druid.maxWait", "60000");
        propertiesMap.put("spring.datasource.druid.timeBetweenEvictionRunsMillis", "60000");
        propertiesMap.put("spring.datasource.druid.minEvictableIdleTimeMillis", "300000");
        propertiesMap.put("spring.datasource.druid.validationQuery", "SELECT 1");
        propertiesMap.put("spring.datasource.druid.testWhileIdle", "true");
        propertiesMap.put("spring.datasource.druid.testOnBorrow", "false");
        propertiesMap.put("spring.datasource.druid.testOnReturn", "false");
        propertiesMap.put("spring.datasource.druid.poolPreparedStatements", "true");
        propertiesMap.put("spring.datasource.druid.maxPoolPreparedStatementPerConnectionSize", "20");
        propertiesMap.put("spring.datasource.druid.filters", "stat,wall");
        propertiesMap.put("spring.datasource.druid.connectionProperties", "druid.stat.mergeSql=true;druid.stat.slowSql");
        propertiesMap.put("spring.datasource.druid.stat-view-servlet.login-username", "root");
        propertiesMap.put("spring.datasource.druid.stat-view-servlet.login-password", "root");
    }

    @Override
    public void onStgProfile(Map<String, Object> propertiesMap) {

    }

    @Override
    public void onPrdProfile(Map<String, Object> propertiesMap) {

    }

    @Override
    public void onAllProfile(Map<String, Object> propertiesMap) {

    }
}
