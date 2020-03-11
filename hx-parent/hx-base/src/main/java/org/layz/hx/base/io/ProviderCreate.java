package org.layz.hx.base.io;

public class ProviderCreate extends AbstractCreateCode {
    private String simpleName;
    @Override
    protected void setClassName() {
        String name1 = clazz.getName();
        int indexOf1 = name1.lastIndexOf(entity);
        String pack = name1.substring(0,indexOf1) + "provider";
        String[] split = name1.split("\\.");
        int index = split.length - 2;
        String modelName = split[index];
        simpleName = modelName.substring(0,1).toUpperCase() + modelName.substring(1) +  "Provider";
        className = pack +  "." + modelName + "." + simpleName;
    }

    @Override
    protected void setContent() {
        content.append("\npublic class " + simpleName + "{\n");

        importContent.append("import org.slf4j.Logger;\n")
                .append("import org.slf4j.LoggerFactory;\n")
                .append("import org.apache.dubbo.container.Main;\n");
        content.append(String.format("\n    private static final Logger LOGGER = LoggerFactory.getLogger(%s.class);\n",
                className.substring(className.lastIndexOf(".") + 1)));

        content.append("\n    public static void main(String[] args) {")
                .append("\n        Main.main(args);")
                .append("\n        LOGGER.debug(\"provider start...\");")
                .append("\n    }\n");
    }
}
