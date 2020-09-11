package cn.ishow.boot.vaild;

/**
 * @author yinchong
 * @create 2020/9/11 21:08
 * @description
 */
public class ApplicationFileCheck implements IFileCheck {

    @Override
    public void check(String fileName, String line,int number) {
        if (!fileName.startsWith("application.") && !fileName.startsWith("application-prd.")) {
            return;
        }

        if (line.startsWith("#")) {
            return;
        }

        for (String skip : BadProperteisHolder.badPropertiesSet) {
            if (line.contains(skip)) {
                System.out.println("文件：" + fileName +" 行数："+number+ " 内容：" + line);
            }
        }

    }
}
