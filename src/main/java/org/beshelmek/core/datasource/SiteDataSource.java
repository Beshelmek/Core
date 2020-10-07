package org.beshelmek.core.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class SiteDataSource extends HikariDataSource {
    private static HikariDataSource ds;

    static {
        ds = new HikariDataSource(new HikariConfig("site_hikari.properties"));
    }

    public DataSource getDataSource(){
        return ds;
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
