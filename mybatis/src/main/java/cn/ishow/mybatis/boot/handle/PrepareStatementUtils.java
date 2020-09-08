package cn.ishow.mybatis.boot.handle;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author yinchong
 * @create 2020/3/22 20:04
 * @description
 */
public class PrepareStatementUtils {

  public static void setFlow(Statement statement) {
    try {
      PreparedStatement ps = (PreparedStatement) statement;
      ps.setFetchSize(Integer.MIN_VALUE);
      ps.setFetchDirection(ResultSet.FETCH_REVERSE);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
