package cn.ishow.mybatis.boot.env;


import cn.ishow.mybatis.boot.asm.MybatisMethodUtils;
import cn.ishow.mybatis.boot.loader.SpringClassReLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author yinchong
 * @create 2020/3/22 12:49
 * @description
 */
@Slf4j
public class MybatisLoaderInject implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        SpringClassReLoader.reload("org.apache.ibatis.executor.statement.PreparedStatementHandler", MybatisMethodUtils.flowHandleResult());
        SpringClassReLoader.reload("org.apache.ibatis.executor.resultset.DefaultResultSetHandler",MybatisMethodUtils.rowHandleInject());
        log.info("reload method success");
    }
}
