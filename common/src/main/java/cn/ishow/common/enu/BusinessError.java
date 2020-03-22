package cn.ishow.common.enu;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BusinessError {
    NO_FIND_ROUTING(401, "没有找到访问路径"),
    BIND_PARAM_FAIL(402, "参数绑定异常"),
    SERVER_FAIL(501,"服务器内部错误"),
    PARAM_VERIFY_FAIL(403, "参数校验失败"),
    NO_LOGIN_FAIL(404,"用户未登陆")
    ;
    private int code;
    private String message;
}