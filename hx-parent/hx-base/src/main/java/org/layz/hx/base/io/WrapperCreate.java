package org.layz.hx.base.io;

public class WrapperCreate extends AbstractCreateCode {
    @Override
    protected void setClassName() {
        className = clazz.getName().replace(entity,"wrapper") + "Wrapper";
    }

    @Override
    protected void setContent() {
        String str  = className.substring(className.lastIndexOf(".") + 1);
        content.append("\npublic interface ").append(str)
                .append(" {\n");
    }
}
