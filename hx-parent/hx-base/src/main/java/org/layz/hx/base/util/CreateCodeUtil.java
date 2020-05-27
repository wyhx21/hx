package org.layz.hx.base.util;

import org.layz.hx.base.io.*;

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

    public static void createProvider(Class clazz) throws Exception{
        try (CreateFile createFile = new ProviderCreate()){
            createFile.setClass(clazz);
        }
    }
}
