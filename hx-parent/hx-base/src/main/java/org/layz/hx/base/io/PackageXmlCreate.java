package org.layz.hx.base.io;

import java.io.File;
import java.io.FileOutputStream;

public class PackageXmlCreate implements CreateFile {
    private FileOutputStream fos;
    private String pack;
    @Override
    public void setClass(Class clazz) throws Exception {
        String path = clazz.getResource("/").getPath();
        String fileName = "/src/main/resources/beanxml/spring-demo-package.xml";
        File file = new File(new File(path).getParentFile().getParentFile(),fileName);
        if(file.exists()) {
            System.out.println(file.getAbsoluteFile() + " exisist");
            return;
        }
        System.out.println(file.getAbsoluteFile());
        File parentFile = file.getParentFile();
        if(!parentFile.exists()) {
            parentFile.mkdirs();
        }
        fos = new FileOutputStream(file);
        getpack(clazz);
        String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<beans xmlns=\"http://www.springframework.org/schema/beans\"\n" +
                        "   xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                        "   xmlns:mybatis=\"http://mybatis.org/schema/mybatis-spring\"\n" +
                        "   xmlns:context=\"http://www.springframework.org/schema/context\"\n" +
                        "   xmlns:dubbo=\"http://dubbo.apache.org/schema/dubbo\"\n" +
                        "   xmlns:nacos=\"http://nacos.io/schema/nacos\"\n" +
                        "   xsi:schemaLocation=\"http://www.springframework.org/schema/beans\n" +
                        "        http://www.springframework.org/schema/beans/spring-beans-4.0.xsd\n" +
                        "        http://mybatis.org/schema/mybatis-spring\n" +
                        "        http://mybatis.org/schema/mybatis-spring.xsd\n" +
                        "        http://nacos.io/schema/nacos\n" +
                        "        http://nacos.io/schema/nacos.xsd\n" +
                        "        http://www.springframework.org/schema/context\n" +
                        "        http://www.springframework.org/schema/context/spring-context-4.0.xsd\n" +
                        "    \thttp://dubbo.apache.org/schema/dubbo\n" +
                        "    \thttp://dubbo.apache.org/schema/dubbo/dubbo.xsd\">\n" +
                        "\n" +
                        "    <import resource=\"classpath*:beanxml/spring-hx-datasource.xml\"/>\n" +
                        "\n" +
                        "    <nacos:annotation-driven />\n" +
                        "    <nacos:global-properties server-addr=\"localhost:8848\" namespace=\"devconfig\" />\n" +
                        "    <nacos:property-source data-id=\"dubbo\" group-id=\"demo\" auto-refreshed=\"true\"/>\n" +
                        "    <nacos:property-source data-id=\"jdbc\" group-id=\"demo\"/>\n" +
                        "\n" +
                        "    <!-- 声明需要暴露的服务接口 -->\n" +
                        "    <dubbo:annotation package=\"" + this.pack + "delegate\"/>\n" +
                        "    <mybatis:scan base-package=\"" + this.pack + "persist.mapper\"/>\n" +
                        "    <context:component-scan base-package=\"" + this.pack + "service\"/>\n" +
                        "    <context:component-scan base-package=\"" + this.pack + "persist.dao\"/>\n" +
                        "</beans>";
        fos.write(content.getBytes("utf-8"));
    }

    @Override
    public void close() throws Exception {
        if(null!=fos) {
            fos.flush();
            fos.close();
        }
    }

    private void getpack(Class clazz){
        String name1 = clazz.getName();
        int indexOf1 = name1.lastIndexOf(entity);
        this.pack = name1.substring(0,indexOf1);
    }
}
