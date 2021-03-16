package cn.ishow.log;

import org.apache.logging.log4j.message.AbstractMessageFactory;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.ParameterizedMessageFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author yinchong
 * @create 2021/3/16 22:08
 * @description
 */
public class CustomeMessageFactory extends AbstractMessageFactory {
    /**
     * 这是log4j2中默认消息格式化工厂
     **/
    private ParameterizedMessageFactory proxy = ParameterizedMessageFactory.INSTANCE;


    public CustomeMessageFactory() {

    }


    private Object handleParameter(Object param) {
        List<ParameterResovler> list = CombineParameterHandler.getInstance().listParameterResolver();
        if (!CollectionUtils.isEmpty(list)) {
            for (ParameterResovler resovler : list) {
                param = resovler.resovle(param);
            }
        }
        return param;
    }


    @Override
    public Message newMessage(final String message, final Object... params) {
        int len = params.length;
        for(int i=0;i<len;i++){
            params[i]=handleParameter(params[i]);
        }
        return proxy.newMessage(message, params);
    }

    /**
     * @since 2.6.1
     */
    @Override
    public Message newMessage(final String message, final Object p0) {
        Object arg0 =handleParameter(p0);
        return proxy.newMessage(message, arg0);
    }

    /**
     * @since 2.6.1
     */
    @Override
    public Message newMessage(final String message, final Object p0, final Object p1) {
        Object arg0 =handleParameter(p0);
        Object arg1 =handleParameter(p1);
        return proxy.newMessage(message, arg0, arg1);
    }

    /**
     * @since 2.6.1
     */
    @Override
    public Message newMessage(final String message, final Object p0, final Object p1, final Object p2) {
        Object arg0 =handleParameter(p0);
        Object arg1 =handleParameter(p1);
        Object arg2 =handleParameter(p2);
        return proxy.newMessage(message, arg0, arg1, arg2);
    }

    /**
     * @since 2.6.1
     */
    @Override
    public Message newMessage(final String message, final Object p0, final Object p1, final Object p2, final Object p3) {
        Object arg0 =handleParameter(p0);
        Object arg1 =handleParameter(p1);
        Object arg2 =handleParameter(p2);
        Object arg3 =handleParameter(p3);
        return proxy.newMessage(message, arg0, arg1, arg2, arg3);
    }

    /**
     * @since 2.6.1
     */
    @Override
    public Message newMessage(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4) {
        Object arg0 =handleParameter(p0);
        Object arg1 =handleParameter(p1);
        Object arg2 =handleParameter(p2);
        Object arg3 =handleParameter(p3);
        Object arg4 =handleParameter(p4);
        return proxy.newMessage(message, arg0, arg1, arg2, arg3, arg4);
    }

    /**
     * @since 2.6.1
     */
    @Override
    public Message newMessage(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5) {
        Object arg0 =handleParameter(p0);
        Object arg1 =handleParameter(p1);
        Object arg2 =handleParameter(p2);
        Object arg3 =handleParameter(p3);
        Object arg4 =handleParameter(p4);
        Object arg5 =handleParameter(p5);
        return proxy.newMessage(message, arg0, arg1, arg2, arg3, arg4, arg5);
    }

    /**
     * @since 2.6.1
     */
    @Override
    public Message newMessage(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5,
                              final Object p6) {
        Object arg0 =handleParameter(p0);
        Object arg1 =handleParameter(p1);
        Object arg2 =handleParameter(p2);
        Object arg3 =handleParameter(p3);
        Object arg4 =handleParameter(p4);
        Object arg5 =handleParameter(p5);
        Object arg6 =handleParameter(p6);
        return proxy.newMessage(message, arg0, arg1, arg2, arg3, arg4, arg5,arg6);
    }

    /**
     * @since 2.6.1
     */
    @Override
    public Message newMessage(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5,
                              final Object p6, final Object p7) {
        Object arg0 =handleParameter(p0);
        Object arg1 =handleParameter(p1);
        Object arg2 =handleParameter(p2);
        Object arg3 =handleParameter(p3);
        Object arg4 =handleParameter(p4);
        Object arg5 =handleParameter(p5);
        Object arg6 =handleParameter(p6);
        Object arg7 = handleParameter(p7);
        return proxy.newMessage(message, arg0, arg1, arg2, arg3, arg4, arg5,arg6,arg7);
    }

    /**
     * @since 2.6.1
     */
    @Override
    public Message newMessage(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5,
                              final Object p6, final Object p7, final Object p8) {
        Object arg0 =handleParameter(p0);
        Object arg1 =handleParameter(p1);
        Object arg2 =handleParameter(p2);
        Object arg3 =handleParameter(p3);
        Object arg4 =handleParameter(p4);
        Object arg5 =handleParameter(p5);
        Object arg6 =handleParameter(p6);
        Object arg7 = handleParameter(p7);
        Object arg8 = handleParameter(p8);
        return proxy.newMessage(message, arg0, arg1, arg2, arg3, arg4, arg5,arg6,arg7,arg8);
    }

    /**
     * @since 2.6.1
     */
    @Override
    public Message newMessage(final String message, final Object p0, final Object p1, final Object p2, final Object p3, final Object p4, final Object p5,
                              final Object p6, final Object p7, final Object p8, final Object p9) {
        Object arg0 =handleParameter(p0);
        Object arg1 =handleParameter(p1);
        Object arg2 =handleParameter(p2);
        Object arg3 =handleParameter(p3);
        Object arg4 =handleParameter(p4);
        Object arg5 =handleParameter(p5);
        Object arg6 =handleParameter(p6);
        Object arg7 = handleParameter(p7);
        Object arg8 = handleParameter(p8);
        Object arg9 = handleParameter(p9);
        return proxy.newMessage(message, arg0, arg1, arg2, arg3, arg4, arg5,arg6,arg7,arg8,arg9);
    }
}
