package org.layz.hx.persist.sql;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

public class JdbcUtil {

    private Properties loadPropertie(String path) throws IOException {
        if(StringUtils.isBlank(path)) {
            path = "/prop/spring-persist.properties";
        }
        Resource resource = new ClassPathResource(path);
        Properties properties = new Properties();
        properties.load(resource.getInputStream());
        return properties;
    }

    private DataSource getDataSource(Properties properties){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(properties.getProperty("hx.spring.jdbc.driver"));
        dataSource.setJdbcUrl(properties.getProperty("hx.spring.jdbc.url"));
        dataSource.setUsername(properties.getProperty("hx.spring.jdbc.username"));
        dataSource.setPassword(properties.getProperty("hx.spring.jdbc.password"));
        return dataSource;
    }

    public JdbcTemplate getJdbcTemplate(String path) throws IOException{
        Properties properties = this.loadPropertie(path);
        DataSource dataSource = this.getDataSource(properties);
        return new JdbcTemplate(dataSource);
    }
}
