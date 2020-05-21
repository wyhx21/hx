package org.layz.hx.persist.sqlBuilder;

import org.layz.hx.core.pojo.info.TableClassInfo;
import org.layz.hx.persist.inte.Const;
import org.layz.hx.persist.util.SqlBuildUtil;

public class FindAllSqlBuilder implements SqlBuilder {

    @Override
    public String getType() {
        return Const.FIND_ALL;
    }

    @Override
    public String buildSql(Object[] param, TableClassInfo tableClassInfo) {
        return SqlBuildUtil.builderSelect(tableClassInfo).toString();
    }

    @Override
    public Object[] buildArgs(Object[] param, TableClassInfo tableClassInfo) {
        return new Object[0];
    }
}
