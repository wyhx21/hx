package org.layz.hx.base.io.service;

import org.layz.hx.base.io.AbstractCreateCode;

public class DaoCreate extends AbstractCreateCode {
    @Override
    protected void setClassName() {
        className = clazz.getName().replace(entity,"persist.dao") + "Dao";
    }

    @Override
    protected void setContent() {
        String str  = className.substring(className.lastIndexOf(".") + 1);
        importContent.append("import org.layz.hx.persist.repository.BaseDao;\n");
        importContent.append("import ").append(clazz.getName()).append(";\n");
        content.append("\npublic interface ").append(str)
                .append(String.format(" extends BaseDao<%s>",clazz.getSimpleName()))
                .append(" {\n");
    }
}
