package cn.ishow.mybatis.boot.exception;


/**
 * @author yinchong
 * @create 2020/3/22 10:42
 * @description 记录数太多异常
 */
public class ToManyRecordException extends RuntimeException {

  public ToManyRecordException(String message) {
    super(message);
  }
}
