package cn.bucheng.mybatis.core.asm;
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import cn.bucheng.mybatis.core.accept.MethodAccept;
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
     * @param injectMethodName
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
