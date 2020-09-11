package cn.ishow.boot.vaild;

import java.util.HashSet;
import java.util.Set;

/**
 * @author yinchong
 * @create 2020/9/11 21:14
 * @description
 */
public class BadProperteisHolder {
    public static Set<String> badPropertiesSet = new HashSet<>(30);

    static {
        badPropertiesSet.add("TEST");
        badPropertiesSet.add("test");
        badPropertiesSet.add("stg");
        badPropertiesSet.add("STG");
    }
}
