package rusk.test.db;

import jp.classmethod.testing.database.DbUnitTester;
import jp.classmethod.testing.database.JdbcDatabaseConnectionManager;

/**
 * Rusk 開発用の DbUnitTester
 */
public class RuskDBTester extends DbUnitTester {

    public RuskDBTester() {
        super(new RuskJdbcDatabaseConnectionManager());
    }

    private static class RuskJdbcDatabaseConnectionManager extends JdbcDatabaseConnectionManager {
        RuskJdbcDatabaseConnectionManager() {
            super("org.hsqldb.jdbcDriver", "jdbc:hsqldb:file:testdb/rusk;shutdown=true");
            super.username = "SA";
            super.password = "";
        }
    }
}
