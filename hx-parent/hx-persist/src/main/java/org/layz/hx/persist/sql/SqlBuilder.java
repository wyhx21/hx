package org.layz.hx.persist.sql;

import org.layz.hx.core.pojo.info.FieldColumnInfo;
import org.layz.hx.core.pojo.info.TableClassInfo;
import org.layz.hx.core.support.HxTableSupport;

public class SqlBuilder {
    private TableClassInfo classInfo;

    public SqlBuilder(Class clazz) {
        this.classInfo = HxTableSupport.getTableClassInfo(clazz);
    }

    public String buildCreateSql() {
        StringBuilder builder = new StringBuilder(String.format("CREATE TABLE `%s` (\n",classInfo.getTableName()));
        for (FieldColumnInfo columnInfo : classInfo.getFieldList()) {
            builder.append(String.format("    `%s` %s,\n", columnInfo.getColumnName(),columnInfo.getColumn().definition()));
        }
        builder.append(String.format("    PRIMARY KEY (`%s`)\n",classInfo.getId()));
        builder.append(String.format(") %s;",classInfo.getTable().definition()));
        return builder.toString();
    }

    public String buildDelete() {
        return String.format("DROP TABLE IF EXISTS `%s`;",classInfo.getTableName());
    }
}
