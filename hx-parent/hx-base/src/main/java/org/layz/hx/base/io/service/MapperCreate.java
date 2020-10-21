package org.layz.hx.base.io.service;

import org.layz.hx.base.io.AbstractCreateCode;

public class MapperCreate extends AbstractCreateCode {

    @Override
    protected void setClassName(){
        className = clazz.getName().replace(entity,"persist.mapper") + "Mapper";
    }

    @Override
    protected void setContent() {
        String str  = className.substring(className.lastIndexOf(".") + 1);
        content.append("public interface ").append(str).append(" {\n");
    }
}
