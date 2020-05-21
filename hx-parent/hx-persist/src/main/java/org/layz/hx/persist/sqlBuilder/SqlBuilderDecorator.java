package org.layz.hx.persist.sqlBuilder;

import org.layz.hx.core.pojo.info.TableClassInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class SqlBuilderDecorator implements SqlBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(SqlBuilderDecorator.class);
    private SqlBuilder sqlBuilder;

    public SqlBuilderDecorator(SqlBuilder sqlBuilder) {
        this.sqlBuilder = sqlBuilder;
    }

    @Override
    public String getType() {
        return sqlBuilder.getType();
    }

    @Override
    public String buildSql(Object[] param, TableClassInfo tableClassInfo) {
        String sql = sqlBuilder.buildSql(param, tableClassInfo);
        LOGGER.debug("hx persist sql  ==> {}", sql);
        return sql;
    }

    @Override
    public Object[] buildArgs(Object[] param, TableClassInfo tableClassInfo) {
        Object[] args = sqlBuilder.buildArgs(param, tableClassInfo);
        LOGGER.debug("hx persist args ==> {}", Arrays.toString(args));
        return args;
    }
}
