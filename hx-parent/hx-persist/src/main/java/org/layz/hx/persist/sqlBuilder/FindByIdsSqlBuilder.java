package org.layz.hx.persist.sqlBuilder;

import org.layz.hx.core.pojo.info.TableClassInfo;
import org.layz.hx.persist.inte.Const;
import org.layz.hx.persist.util.SqlBuildUtil;

import java.util.List;

public class FindByIdsSqlBuilder implements SqlBuilder {
    @Override
    public String getType() {
        return Const.FIND_BY_IDS;
    }

    @Override
    public String buildSql(Object[] param, TableClassInfo tableClassInfo) {
        StringBuilder sql = SqlBuildUtil.builderSelect(tableClassInfo)
                .append(" where ").append(tableClassInfo.getId()).append(" in (");
        List<Long> ids = (List<Long>) param[0];
        for(int i = 0; i < ids.size(); i++) {
            if(i == 0) {
                sql.append(" ? ");
            } else {
                sql.append(",? ");
            }
        }
        sql.append(" );");
        return sql.toString();
    }

    @Override
    public Object[] buildArgs(Object[] param, TableClassInfo tableClassInfo) {
        List<Long> ids = (List<Long>) param[0];
        return ids.toArray();
    }
}
