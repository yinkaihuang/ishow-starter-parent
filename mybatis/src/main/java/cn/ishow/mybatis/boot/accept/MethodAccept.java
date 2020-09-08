package cn.ishow.mybatis.boot.accept;

import org.objectweb.asm.MethodVisitor;

import java.util.Set;
public interface MethodAccept {
    /**
     *
     * @param methodName 当前调用是的方法名称
     * @param  injectMethodNames 需要注入的方法
     * @param supper
     * @return
     */
    MethodVisitor accept(String methodName, Set<String> injectMethodNames, MethodVisitor supper);
}
