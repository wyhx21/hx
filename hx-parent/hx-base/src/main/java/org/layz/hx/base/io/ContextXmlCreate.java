package org.layz.hx.base.io;

import java.io.File;
import java.io.FileOutputStream;

public class ContextXmlCreate implements CreateFile {
    private FileOutputStream fos;
    @Override
    public void setClass(Class clazz) throws Exception {
        String path = clazz.getResource("/").getPath();
        String fileName = "/src/main/resources/META-INF/spring/spring-context.xml";
        File file = new File(new File(path).getParentFile().getParentFile(),fileName);
        System.out.println(file.getAbsoluteFile());
        File parentFile = file.getParentFile();
        if(!parentFile.exists()) {
            parentFile.mkdirs();
        }
        fos = new FileOutputStream(file);
        StringBuilder str = new StringBuilder()
                .append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
                .append("<beans xmlns=\"http://www.springframework.org/schema/beans\"\n")
                .append("    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n")
                .append("    xsi:schemaLocation=\"http://www.springframework.org/schema/beans\n")
                .append("        http://www.springframework.org/schema/beans/spring-beans-4.0.xsd\">\n")
                .append("\n    <import resource=\"classpath*:beanxml/spring-hx-content.xml\"/>\n")
                .append("</beans>");
        fos.write(str.toString().getBytes("utf-8"));
    }

    @Override
    public void close() throws Exception {
        fos.flush();
        fos.close();
    }
}
