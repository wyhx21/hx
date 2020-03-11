package org.layz.hx.base.io;

import java.io.File;
import java.io.FileOutputStream;

public class MapperXmlCreate implements CreateFile {
    private FileOutputStream fos;
    @Override
    public void setClass(Class clazz) throws Exception {
        String path = clazz.getResource("/").getPath();
        String basePack = "/src/main/resources/mappers/";
        String clazzName = clazz.getName();
        basePack +=  clazzName.substring(clazzName.lastIndexOf("entity") + 7)
                .replace(".","/") + "Mapper.xml";
        File file = new File(new File(path).getParentFile().getParentFile(),basePack);
        System.out.println(file.getAbsoluteFile());
        File parentFile = file.getParentFile();
        if(!parentFile.exists()) {
            parentFile.mkdirs();
        }
        fos = new FileOutputStream(file);
        fos.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n".getBytes("utf-8"));
        fos.write("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"\n".getBytes("utf-8"));
        fos.write("    \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n".getBytes("utf-8"));
        String className = clazz.getName().replace(entity,"persist.mapper") + "Mapper";
        fos.write(String.format("<mapper namespace=\"%s\">\n\n",className).getBytes("utf-8"));
        fos.write("</mapper>\n".getBytes("utf-8"));
    }

    @Override
    public void close() throws Exception {
        fos.flush();
        fos.close();
    }
}
