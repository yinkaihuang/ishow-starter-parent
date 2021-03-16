package cn.ishow.log;

import java.util.LinkedList;
import java.util.List;

/**
 * @author yinchong
 * @create 2021/3/16 22:33
 * @description
 */
public class CombineParameterHandler {

    private static CombineParameterHandler INSTANCE = new CombineParameterHandler();

    private List<ParameterResovler> resovlers = new LinkedList<>();

    public static CombineParameterHandler getInstance() {
        return INSTANCE;
    }

    private CombineParameterHandler() {
    }


    public void addParameterResolver(ParameterResovler resovler) {
        resovlers.add(resovler);
    }


    public List<ParameterResovler> listParameterResolver() {
        return resovlers;
    }

}
