package org.layz.hx.persist.sql;

import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;

public class CreateTableUtil {
    /**
     * @param clazz
     * @throws IOException
     */
    public static void createTable(Class clazz) throws IOException {
        createTable(clazz,null);
    }

    /**
     * @param clazz
     * @param path
     * @throws IOException
     */
    public static void createTable(Class clazz,String path) throws IOException {
        JdbcUtil jdbcUtil = new JdbcUtil();
        JdbcTemplate jdbcTemplate = jdbcUtil.getJdbcTemplate(path);
        SqlBuilder sqlBuilder = new SqlBuilder(clazz);
        jdbcTemplate.update(sqlBuilder.buildDelete());
        jdbcTemplate.update(sqlBuilder.buildCreateSql());
    }

}
