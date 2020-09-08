package cn.ishow.mybatis.boot.asm;
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

import cn.ishow.mybatis.boot.accept.MethodAccept;
import org.objectweb.asm.MethodVisitor;

import java.util.HashSet;
import java.util.Set;

import static org.objectweb.asm.Opcodes.*;

/**
 * @author yinchong
 * @create 2020/3/22 11:05
 * @description
 */
public class MybatisMethodUtils {

    /**
     * 请求数量过大限制
     *
     * @return
     */
    public static byte[] rowHandleInject() {
        try {
            Set<String> injectMethods = new HashSet<>(1);
            injectMethods.add("storeObject");
            return ASMUtils.methodInject("org.apache.ibatis.executor.resultset.DefaultResultSetHandler", injectMethods,
                    new MethodAccept() {
                        @Override
                        public MethodVisitor accept(String methodName, Set<String> injectMethodName, MethodVisitor methodVisitor) {
                            return new MethodVisitor(ASM7, methodVisitor) {
                                @Override
                                public void visitCode() {
                                    if (!injectMethodName.contains(methodName)) {
                                        methodVisitor.visitCode();
                                        return;
                                    }
                                    methodVisitor.visitVarInsn(ALOAD, 2);
                                    methodVisitor.visitMethodInsn(INVOKESTATIC, "cn/ishow/mybatis/boot/handle/DefaultRecordLimitHandler", "handleRecord", "(Lorg/apache/ibatis/executor/result/DefaultResultContext;)V", false);
                                    super.visitCode();
                                }
                            };
                        }
                    });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 结果流逝处理
     *
     * @return
     */
    public static byte[] flowHandleResult() {
        try {
            Set<String> injectMethod = new HashSet<>(2);
            injectMethod.add("query");
            injectMethod.add("queryCursor");
            return ASMUtils.methodInject("org.apache.ibatis.executor.statement.PreparedStatementHandler", injectMethod, new MethodAccept() {
                @Override
                public MethodVisitor accept(String methodName, Set<String> injectMethodName, MethodVisitor methodVisitor) {
                    if (methodName.equals("query")) {
                        return new MethodVisitor(ASM7, methodVisitor) {
                            @Override
                            public void visitCode() {
                                methodVisitor.visitVarInsn(ALOAD, 1);
                                methodVisitor.visitMethodInsn(INVOKESTATIC, "cn/ishow/mybatis/boot/handle/PrepareStatementUtils", "setFlow", "(Ljava/sql/Statement;)V", false);
                                super.visitCode();
                            }
                        };
                    } else if (methodName.equals("queryCursor")) {
                        return new MethodVisitor(ASM7, methodVisitor) {
                            @Override
                            public void visitCode() {
                                methodVisitor.visitVarInsn(ALOAD, 1);
                                methodVisitor.visitMethodInsn(INVOKESTATIC, "cn/ishow/mybatis/boot/handle/PrepareStatementUtils", "setFlow", "(Ljava/sql/Statement;)V", false);
                                super.visitCode();
                            }
                        };
                    }
                    return methodVisitor;
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
