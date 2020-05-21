package org.layz.hx.persist.util;

import org.layz.hx.base.pojo.Pageable;
import org.layz.hx.base.util.StringUtil;
import org.layz.hx.core.pojo.info.FieldColumnInfo;
import org.layz.hx.core.pojo.info.TableClassInfo;
import org.layz.hx.persist.inte.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SqlBuildUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(SqlBuildUtil.class);

    public static StringBuilder builderSelect(TableClassInfo tableClassInfo) {
        String tableName = tableClassInfo.getTableName();
        List<FieldColumnInfo> fieldList = tableClassInfo.getFieldList();
        StringBuilder sqlBuilder = new StringBuilder("select ");
        for (int i = 0; i < fieldList.size(); i++) {
            FieldColumnInfo fieldColumnInfo = fieldList.get(i);
            String columnName = fieldColumnInfo.getColumnName();
            if(i > 0) {
                sqlBuilder.append(Const.SEPARATOR);
            }
            sqlBuilder.append(columnName);
        }
        sqlBuilder.append(" from ") .append(tableName);
        return sqlBuilder;
    }

    public static void buildWhereSql(StringBuilder sql, TableClassInfo tableClassInfo, Object[] param){
        Object object = param.length > 0 ? param[0] : null;
        Pageable pageable =  param.length > 1 ? (Pageable)param[1] : null;

        if(null != object) {
            int index = 0;
            List<FieldColumnInfo> fieldList = tableClassInfo.getFieldList();
            for (FieldColumnInfo fieldColumnInfo : fieldList) {
                Class<?> fieldType = fieldColumnInfo.getFieldType();
                Method methodGet = fieldColumnInfo.getMethodGet();
                try {
                    Object value = methodGet.invoke(object);
                    if(null == value) {
                        continue;
                    }
                    if(String.class == fieldType && StringUtil.isBlank(value.toString())) {
                        continue;
                    }
                    if(index == 0) {
                        sql.append(" where ").append(fieldColumnInfo.getColumnName()).append(" = ?");
                        index ++;
                    } else {
                        sql.append(" and ").append(fieldColumnInfo.getColumnName()).append(" = ?");
                    }
                } catch (Exception e) {
                    LOGGER.error("buildSql error, method: {}", methodGet, e);
                }
            }
        }
        if(null != pageable) {
            String orderBy = pageable.getOrderBy();
            if(null != orderBy && orderBy.length() > 1) {
                sql.append(" order by ").append(orderBy);
            }
            Long offset = pageable.getOffset();
            if(null != offset) {
                sql.append(" limit ? , ? ");
            }
        }
    }

    public static Object[] buildWhereArgs(TableClassInfo tableClassInfo, Object[] param){
        Object object = param.length > 0 ? param[0] : null;
        Pageable pageable =  param.length > 1 ? (Pageable)param[1] : null;

        List<Object> argsList = new ArrayList<Object>();
        if(null != object) {
            List<FieldColumnInfo> fieldList = tableClassInfo.getFieldList();
            for (FieldColumnInfo fieldColumnInfo : fieldList) {
                Class<?> fieldType = fieldColumnInfo.getFieldType();
                Method methodGet = fieldColumnInfo.getMethodGet();
                try {
                    Object value = methodGet.invoke(object);
                    if(null == value) {
                        continue;
                    }
                    if(String.class == fieldType && StringUtil.isBlank(value.toString())) {
                        continue;
                    }
                    argsList.add(value);
                } catch (Exception e) {
                    LOGGER.error("buildSql error, method: {}", methodGet, e);
                }
            }
        }
        if(null != pageable) {
            Long offset = pageable.getOffset();
            if(null != offset) {
                argsList.add(offset);
                argsList.add(pageable.getSize());
            }
        }
        return argsList.toArray();
    }
}
