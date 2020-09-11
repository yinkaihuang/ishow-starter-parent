package cn.ishow.boot.vaild;

import org.apache.logging.log4j.util.Strings;

import java.util.Arrays;
import java.util.List;

/**
 * @author yinchong
 * @create 2020/9/11 21:03
 * @description
 */
public class ComposeFileCheck implements IFileCheck {

    private List<String> javaSkipList = Arrays.asList("import", "package", "//", "/*", "*");

    private List<IFileCheck> fileChecks = Arrays.asList(new JavaFileCheck(), new ApplicationFileCheck());


    @Override
    public void check(String fileName, String line, int number) {
        if (fileName.endsWith(".java")) {
            if (Strings.isBlank(line)) {
                return;
            }
            line = line.trim();
            for (String skip : javaSkipList) {
                if (line.startsWith(skip)) {
                    return;
                }
            }
        }
        for (IFileCheck fileCheck : fileChecks) {
            fileCheck.check(fileName, line, number);
        }
    }
}
