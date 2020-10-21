package org.layz.hx.base.io.service;

import org.layz.hx.base.io.AbstractCreateCode;

public class DelegateImplCreate extends AbstractCreateCode {
    @Override
    protected void setClassName() {
        className = clazz.getName().replace(entity,"delegate") + "DelegateImpl";
    }

    @Override
    protected void setContent() {
    	
        String str  = className.substring(className.lastIndexOf(".") + 1);
        importContent.append("import org.apache.dubbo.config.annotation.DubboService;\n");
        content.append("\n@DubboService")
                .append("\npublic class ").append(str)
                .append(" {\n");

        super.appendLog();

        String simpleName = clazz.getSimpleName();
        String importService = clazz.getName().replace(entity,"service") + "Service";
        importContent.append("import " + importService + ";\n");
        String service = simpleName.substring(0,1).toLowerCase() + simpleName.substring(1) + "Service";
        content.append("\n    @Autowired")
            .append("\n    private " + simpleName + "Service ")
            .append(service + ";\n");
    }
}
