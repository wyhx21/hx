package org.layz.hx.base.io;

public class ControllerCreate extends AbstractCreateCode {
    @Override
    protected void setClassName() {
        className = clazz.getName().replace(entity,"controller") + "Controller";
    }

    @Override
    protected void setContent() {
        String str  = className.substring(className.lastIndexOf(".") + 1);
        importContent.append("import org.springframework.web.bind.annotation.RequestMapping;\n");
        importContent.append("import org.springframework.web.bind.annotation.RestController;\n");
        content.append("\n@RestController")
                .append("\n// @RequestMapping(\"/api\")")
                .append("\npublic class ").append(str)
                .append(" {\n");

        super.appendLog();

        content.append("\n    @Autowired");
        String wrapperName = clazz.getName().replace(entity,"wrapper") + "Wrapper";
        importContent.append("import ").append(wrapperName).append(";\n");

        String simpleName = clazz.getSimpleName();
        String wrapper = simpleName.substring(0,1).toLowerCase() + simpleName.substring(1) + "Wrapper";
        content.append("\n    private " + simpleName + "Wrapper ")
                .append(wrapper).append(";\n");
    }
}
