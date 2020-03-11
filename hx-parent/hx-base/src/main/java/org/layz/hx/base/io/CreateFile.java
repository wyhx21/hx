package org.layz.hx.base.io;

public interface CreateFile extends AutoCloseable{
    String entity = "entity";

    void setClass(Class clazz) throws Exception;
}
