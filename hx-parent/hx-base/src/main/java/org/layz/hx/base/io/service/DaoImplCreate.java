package org.layz.hx.base.io.service;

import org.layz.hx.base.io.AbstractCreateCode;

public class DaoImplCreate extends AbstractCreateCode {
    @Override
    protected void setClassName() {
        className = clazz.getName().replace(entity,"persist.dao") + "DaoImpl";
    }

    @Override
    protected void setContent() {
        String str  = className.substring(className.lastIndexOf(".") + 1);
        importContent.append("import org.layz.hx.persist.repository.BaseDaoImpl;\n");
        importContent.append("import org.springframework.stereotype.Repository;\n");
        importContent.append("import ").append(clazz.getName()).append(";\n");
        content.append("\n@Repository")
                .append("\npublic class ").append(str)
                .append(String.format(" extends BaseDaoImpl<%s>",clazz.getSimpleName()))
                .append(String.format(" implements %sDao",clazz.getSimpleName()))
                .append(" {\n");

        super.appendLog();

        content.append("\n    @Autowired");
        String mapperName = clazz.getName().replace(entity,"persist.mapper") + "Mapper";
        importContent.append("import ").append(mapperName).append(";\n");

        String simpleName = clazz.getSimpleName();
        content.append("\n    private " + simpleName + "Mapper ")
            .append(simpleName.substring(0,1).toLowerCase())
            .append(simpleName.substring(1))
            .append("Mapper;\n");
    }
}
