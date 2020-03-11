package org.layz.hx.persist.sqlBuilder;

import org.layz.hx.core.pojo.info.TableClassInfo;
import org.layz.hx.persist.inte.Const;
import org.layz.hx.persist.pojo.SqlParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class FindByIdsSqlBuilder extends AbstractSqlBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(FindByIdsSqlBuilder.class);

    @Override
    public String getType() {
        return Const.FIND_BY_IDS;
    }

    @Override
    public StringBuilder buildCacheSql(TableClassInfo tableClassInfo, Object[] param) {
        LOGGER.info("buildCacheSql,class: {}", tableClassInfo.getClazz());
        StringBuilder stringBuilder = builderSelect(tableClassInfo);
        return stringBuilder.append(" where ")
                .append(tableClassInfo.getId())
                .append(" in(");
    }

    @Override
    public SqlParam buildSql(StringBuilder cacheSql, TableClassInfo tableClassInfo, Object[] param) {
        List<Long> ids = (List<Long>) param[0];
        StringBuilder whereSql = new StringBuilder();
        for (Long id : ids) {
            whereSql.append(",?");
        }
        StringBuilder sql = new StringBuilder(cacheSql)
                .append(whereSql.substring(1))
                .append(");");

        SqlParam sqlParam = new SqlParam();
        sqlParam.setSql(sql.toString());
        sqlParam.setArgs(ids.toArray());
        return sqlParam;
    }
}
