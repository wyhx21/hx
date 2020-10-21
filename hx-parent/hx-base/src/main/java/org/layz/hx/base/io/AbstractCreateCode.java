package org.layz.hx.base.io;

import java.io.File;
import java.io.FileOutputStream;

public abstract class AbstractCreateCode implements CreateFile {
    private FileOutputStream fos;
    protected Class clazz;
    protected File baseFile;
    protected String className;
    protected StringBuilder importContent = new StringBuilder();
    protected StringBuilder content = new StringBuilder();
    @Override
    public void setClass(Class clazz) throws Exception {
        this.clazz = clazz;
        String path = clazz.getResource("/").getPath();
        String basePack = "/src/main/java";
        baseFile = new File(new File(path).getParentFile().getParentFile(),basePack);
        setClassName();
        createFile();
    }

    protected void createFile() throws Exception{
        String path = className.replaceAll("\\.","/") + ".java";
        File codeFile = new File(baseFile,path);
        if(codeFile.exists()) {
            System.out.println(codeFile.getAbsoluteFile() + " exists");
            return;
        }
        File parentFile = codeFile.getParentFile();
        if(!parentFile.exists()) {
            parentFile.mkdirs();
        }
        codeFile.createNewFile();
        System.out.println(codeFile.getAbsoluteFile());
        fos = new FileOutputStream(codeFile);
        setContent();
        write();
    }

    protected void appendLog(){
        importContent.append("import org.slf4j.Logger;\n")
                .append("import org.slf4j.LoggerFactory;\n")
                .append("import org.springframework.beans.factory.annotation.Autowired;\n");
        content.append(String.format("\n    private static final Logger LOGGER = LoggerFactory.getLogger(%s.class);\n",
                className.substring(className.lastIndexOf(".") + 1)));
    }

    private void write() throws Exception{
        String packName = "package " + className.substring(0,className.lastIndexOf(".")) + ";\n\n";
        fos.write(packName.getBytes("utf-8"));
        fos.write(importContent.toString().getBytes("utf-8"));
        fos.write(content.toString().getBytes("utf-8"));
        fos.write("\n}".getBytes("utf-8"));
    }

    @Override
    public void close() throws Exception {
        if(null != fos) {
            fos.flush();
            fos.close();
        }
    }

    protected abstract void setClassName();

    protected abstract void setContent();
}
