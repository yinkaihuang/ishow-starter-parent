package cn.ishow.mybatis.boot.asm;

import cn.ishow.mybatis.boot.accept.MethodAccept;
import org.objectweb.asm.*;

import java.util.Set;


/**
 * @author yinchong
 * @create 2020/3/22 11:00
 * @description
 */
public class ASMUtils {

    /**
     * 使用ASM进行方法注入
     *
     * @param fullName
     * @param injectMethodNames
     * @param methodAccept
     * @return
     * @throws Exception
     */
    public static byte[] methodInject(String fullName, Set<String> injectMethodNames, MethodAccept methodAccept) throws Exception {
        String fullNameType = fullName.replace(".", "/");
        ClassReader reader = new ClassReader(fullNameType);
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        ClassVisitor visitor = new ClassVisitor(Opcodes.ASM7, writer) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
                if (!injectMethodNames.contains(name)) {
                    return methodVisitor;
                }
                return methodAccept.accept(name, injectMethodNames, methodVisitor);
            }
        };

        reader.accept(visitor, ClassReader.EXPAND_FRAMES);
        byte[] bytes = writer.toByteArray();
        return bytes;
    }


}
