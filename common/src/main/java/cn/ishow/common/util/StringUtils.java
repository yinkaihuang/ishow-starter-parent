package cn.ishow.common.util;

import java.util.regex.Pattern;

/**
 * @author yinchong
 * @create 2021/3/15 21:48
 * @description
 */
public class StringUtils {

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}
