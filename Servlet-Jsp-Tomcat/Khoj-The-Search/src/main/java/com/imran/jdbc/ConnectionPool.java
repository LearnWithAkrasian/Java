package com.imran.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.ResourceBundle;

// This class is optimizing the connection criteria.
// This class establishes pre-connections with the database.
// Storing them as ready-to-use connections in a pool.
// It doesn't close any connections; instead, it preserves them in the pool.
// This follows the singleton design pattern,
// ensuring a single instance is created for the entire JVM.
public final class ConnectionPool {
    private static final ConnectionPool INSTANCE
            = new ConnectionPool();
    private ConnectionPool(){}

    public static ConnectionPool getInstance() {
        return INSTANCE;
    }


    public DataSource getDataSource() {
        // We utilize the ResourceBundle class
        // for convenient access to data from the db.properties file.
        var dbProb = ResourceBundle.getBundle("db");

        // Configuring the database connection.
        // For utilizing connection pool
        // we used a built-in connection pool object named HikariConfig.
        var config = new HikariConfig();
        config.setJdbcUrl(dbProb.getString("db.url"));
        config.setUsername(dbProb.getString("db.user"));
        config.setPassword(dbProb.getString("db.password"));
        config.setDriverClassName(dbProb.getString("db.driver"));
        var maxPoolSize
                = dbProb.getString("db.max.connections");
        config.setMaximumPoolSize(Integer.parseInt(maxPoolSize));

        return new HikariDataSource(config);
    }
}
