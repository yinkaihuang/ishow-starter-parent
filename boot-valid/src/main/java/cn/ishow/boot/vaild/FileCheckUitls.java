package cn.ishow.boot.vaild;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

/**
 * @author yinchong
 * @create 2020/9/11 21:20
 * @description
 */
public class FileCheckUitls {
    private static ComposeFileCheck fileCheck = new ComposeFileCheck();

    public static void check() {
        long startTime = System.currentTimeMillis();
        try {
            String root = System.getProperty("user.dir");
            recursionCheck(new File(root));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            long endTime = System.currentTimeMillis();
            System.out.println("安全扫描耗时:" + ((endTime - startTime)/1000)+" s");
        }

    }

    /**
     * 添加需要查找拼写错误问题key
     *
     * @param badKey
     */
    public static void add(String... badKey) {
        BadProperteisHolder.badPropertiesSet.addAll(Arrays.asList(badKey));
    }

    private static void recursionCheck(File file) throws Exception {
        if (file.isFile()) {
            String fileName = file.getName();
            if (!fileName.endsWith(".java") && !fileName.startsWith("application.") && !fileName.startsWith("application-prd")) {
                return;
            }
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line = null;
                int number = 0;
                while ((line = reader.readLine()) != null) {
                    number++;
                    fileCheck.check(fileName, line, number);
                }
            }
            return;
        }
        File[] files = file.listFiles();
        for (File temp : files) {
            recursionCheck(temp);
        }
    }
}
