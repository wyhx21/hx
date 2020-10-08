package org.layz.hx.core.visitor;

import org.layz.hx.core.pojo.info.FieldColumnInfo;
import org.layz.hx.core.pojo.info.TableClassInfo;
import org.layz.hx.core.support.HxTableSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.List;

public class SqlDataVisitor implements Visitor {
    private static final Logger LOGGER = LoggerFactory.getLogger(SqlDataVisitor.class);
    @Override
    public Object begin(Object data) {
        TableClassInfo classInfo = HxTableSupport.getTableClassInfo(data.getClass());
        StringBuilder sql = new StringBuilder("insert into ").append(classInfo.getTableName()).append(" (");
        List<FieldColumnInfo> fieldList = classInfo.getFieldList();
        for (int i = 0; i < fieldList.size(); i++) {
            FieldColumnInfo columnInfo = fieldList.get(i);
            if(columnInfo.getColumn().ignore()){
                continue;
            }
            if(i > 0) {
                sql.append(", ");
            }
            sql.append(String.format("`%s`",columnInfo.getColumnName()));
        }
        sql.append(" ) values ");
        return sql.toString();
    }

    @Override
    public Object accept(Object data) {
        TableClassInfo classInfo = HxTableSupport.getTableClassInfo(data.getClass());
        StringBuilder sql = new StringBuilder("(");
        List<FieldColumnInfo> fieldList = classInfo.getFieldList();
        for (int i = 0; i < fieldList.size(); i++) {
            FieldColumnInfo columnInfo = fieldList.get(i);
            if(columnInfo.getColumn().ignore()){
                continue;
            }
            if (i > 0) {
                sql.append(", ");
            }
            Method methodGet = columnInfo.getMethodGet();
            try {
                Object value = methodGet.invoke(data);
                if(null == value) {
                    sql.append("NULL");
                } else if (Boolean.class.isInstance(value)) {
                    sql.append(value);
                } else if (Number.class.isInstance(value)) {
                    sql.append(value);
                } else {
                    sql.append(String.format("\"%s\"",value));
                }
            } catch (Exception e) {
                LOGGER.info("excute error: ", e);
                sql.append("NULL");
            }
        }
        sql.append(")");
        return sql.toString();
    }
}
