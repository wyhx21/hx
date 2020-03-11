package org.layz.hx.base.io;

public class ServiceImplCreate extends AbstractCreateCode {
    @Override
    protected void setClassName() {
        className = clazz.getName().replace(entity,"service") + "ServiceImpl";
    }

    @Override
    protected void setContent() {
        String str  = className.substring(className.lastIndexOf(".") + 1);
        importContent.append("import org.layz.hx.persist.service.BaseServiceImpl;\n");
        importContent.append("import org.springframework.stereotype.Service;\n");
        importContent.append("import ").append(clazz.getName()).append(";\n");
        content.append("\n@Service")
                .append("\npublic class ").append(str)
                .append(String.format(" extends BaseServiceImpl<%s>",clazz.getSimpleName()))
                .append(String.format(" implements %sService ",clazz.getSimpleName()))
                .append(" {\n");

        super.appendLog();

        content.append("\n    @Autowired");
        String daoName = clazz.getName().replace(entity,"persist.dao") + "Dao";
        importContent.append("import ").append(daoName).append(";\n");

        String simpleName = clazz.getSimpleName();
        String dao = simpleName.substring(0,1).toLowerCase() + simpleName.substring(1) + "Dao";
        content.append("\n    private " + simpleName + "Dao ")
                .append(dao).append(";\n");

        importContent.append("import org.layz.hx.persist.repository.BaseDao;\n");
        content.append("\n    @Override");
        content.append(String.format("\n    public BaseDao<%s> getBaseDao() {",simpleName))
                .append("\n        return " + dao + ";")
                .append("\n    }");
    }
}
