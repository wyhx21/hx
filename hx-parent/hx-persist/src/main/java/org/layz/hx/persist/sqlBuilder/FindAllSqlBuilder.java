package org.layz.hx.persist.sqlBuilder;

import org.layz.hx.core.pojo.info.TableClassInfo;
import org.layz.hx.persist.inte.Const;
import org.layz.hx.persist.pojo.SqlParam;

public class FindAllSqlBuilder extends AbstractSqlBuilder {

    @Override
    public String getType() {
        return Const.FIND_ALL;
    }

    @Override
    public StringBuilder buildCacheSql(TableClassInfo tableClassInfo, Object[] param) {
        return builderSelect(tableClassInfo);
    }

    @Override
    public SqlParam buildSql(StringBuilder cacheSql, TableClassInfo tableClassInfo, Object[] param) {
        SqlParam sqlParam = new SqlParam();
        sqlParam.setSql(cacheSql.toString());
        return sqlParam;
    }
}
