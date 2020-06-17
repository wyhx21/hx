package org.layz.hx.base.io;

public class WrapperImplCreate extends AbstractCreateCode {
    @Override
    protected void setClassName() {
        className = clazz.getName().replace(entity,"wrapper") + "WrapperImpl";
    }

    @Override
    protected void setContent() {
        String str  = className.substring(className.lastIndexOf(".") + 1);
        importContent.append("import org.springframework.stereotype.Component;\n");
        content.append("\n@Component")
                .append("\npublic class ").append(str)
                .append(String.format(" implements %sWrapper ",clazz.getSimpleName()))
                .append(" {\n");

        super.appendLog();

        content.append("\n    @Autowired");
        String delegateName = clazz.getName().replace(entity,"delegate") + "Delegate";
        importContent.append("import ").append(delegateName).append(";\n");

        String simpleName = clazz.getSimpleName();
        String delegate = simpleName.substring(0,1).toLowerCase() + simpleName.substring(1) + "Delegate";
        content.append("\n    private " + simpleName + "Delegate ")
                .append(delegate).append(";\n");
    }
}
