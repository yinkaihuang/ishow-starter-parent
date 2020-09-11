package cn.ishow.boot.vaild;

/**
 * @author yinchong
 * @create 2020/9/11 21:08
 * @description
 */
public class JavaFileCheck implements IFileCheck {
    @Override
    public void check(String fileName, String line, int number) {
        if (!fileName.endsWith(".java")) {
            return;
        }
        if (fileName.startsWith("Test") || fileName.endsWith("Test.java")) {
            return;
        }
        for (String badKey : BadProperteisHolder.badPropertiesSet) {
            if (line.contains(badKey)) {
                System.out.println("文件：" + fileName + " 行数：" + number + " 内容：" + line);
                return;
            }
        }

        FeignCheck(fileName, line, number);

    }

    private void FeignCheck(String fileName, String line, int number) {
        if (!line.startsWith("@FeignClient")) {
            return;
        }
        if (!line.contains("url")) {
            return;
        }
        if (line.contains("http://")) {
            System.out.println("文件：" + fileName + " 行数：" + number + " 内容：" + line);
            return;
        }
    }
}
