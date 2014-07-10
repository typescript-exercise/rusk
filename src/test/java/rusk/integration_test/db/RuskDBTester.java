package rusk.integration_test.db;

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
        private static TestDatabaseConfig config = new TestDatabaseConfig();
        
        RuskJdbcDatabaseConnectionManager() {
            super(config.getDriver(), config.getUrl());
            super.username = config.getUser();
            super.password = config.getPassword();
        }
    }
}
