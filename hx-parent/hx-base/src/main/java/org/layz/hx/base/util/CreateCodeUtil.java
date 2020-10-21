package org.layz.hx.base.util;

import org.layz.hx.base.io.*;
import org.layz.hx.base.io.service.*;
import org.layz.hx.base.io.web.ControllerCreate;
import org.layz.hx.base.io.web.WrapperCreate;
import org.layz.hx.base.io.web.WrapperImplCreate;

public class CreateCodeUtil {

    public static void createCode(Class clazz) throws Exception{
        try (CreateFile createFile = new MapperCreate()){
            createFile.setClass(clazz);
        }
        try (CreateFile createFile = new MapperXmlCreate()){
            createFile.setClass(clazz);
        }
        try (CreateFile createFile = new DaoCreate()){
            createFile.setClass(clazz);
        }
        try (CreateFile createFile = new DaoImplCreate()){
            createFile.setClass(clazz);
        }
        try (CreateFile createFile = new ServiceCreate()){
            createFile.setClass(clazz);
        }
        try (CreateFile createFile = new ServiceImplCreate()){
            createFile.setClass(clazz);
        }
        try (CreateFile createFile = new DelegateImplCreate()){
            createFile.setClass(clazz);
        }
    }

    public static void createWebCode(Class clazz) throws Exception{
        try (CreateFile createFile = new WrapperCreate()){
            createFile.setClass(clazz);
        }
        try (CreateFile createFile = new WrapperImplCreate()){
            createFile.setClass(clazz);
        }
        try (CreateFile createFile = new ControllerCreate()){
            createFile.setClass(clazz);
        }
    }

    public static void createProvider(Class clazz) throws Exception{
        try (CreateFile createFile = new ProviderCreate()){
            createFile.setClass(clazz);
        }
        try (CreateFile createFile = new PackageXmlCreate()){
            createFile.setClass(clazz);
        }
    }
}
