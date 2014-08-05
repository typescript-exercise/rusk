package rusk.integration_test.db;

import rusk.persistence.framework.DatabaseConfig;

/**
 * 
 */
public class TestDatabaseConfig implements DatabaseConfig {

    @Override
    public String getUrl() {
        return "jdbc:hsqldb:file:testdb/integration_test;shutdown=true";
    }

    @Override
    public String getDriver() {
        return "org.hsqldb.jdbcDriver";
    }

    @Override
    public String getUser() {
        return "SA";
    }

    @Override
    public String getPassword() {
        return "";
    }
}
