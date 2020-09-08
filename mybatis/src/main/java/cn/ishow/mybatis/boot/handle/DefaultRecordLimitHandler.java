package cn.ishow.mybatis.boot.handle;


import cn.ishow.mybatis.boot.exception.ToManyRecordException;
import org.apache.ibatis.executor.result.DefaultResultContext;

/**
 * @author yinchong
 * @create 2020/3/22 10:44
 * @description
 */
public class DefaultRecordLimitHandler {
    public static volatile int defaultSize = 1000;

    public static void changeDefaultSize(int defaultSize) {
        DefaultRecordLimitHandler.defaultSize = defaultSize;
    }


    public static void handleRecord(DefaultResultContext<Object> context) {
        if (context == null || context.getResultCount() <= defaultSize) {
            return;
        }
        throw new ToManyRecordException("当前查询出来记录数大于默认配置数量：" + defaultSize+" 条记录");
    }
}
