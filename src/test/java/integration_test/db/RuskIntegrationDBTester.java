package integration_test.db;

import jp.classmethod.testing.database.JdbcDatabaseConnectionManager;
import test.db.RuskDBTester;

/**
 * Rusk 開発用の DbUnitTester
 */
public class RuskIntegrationDBTester extends RuskDBTester {

    public RuskIntegrationDBTester() {
        super(new RuskJdbcDatabaseConnectionManager(), null);
    }

    public RuskIntegrationDBTester(Class<?> testClass) {
        super(new RuskJdbcDatabaseConnectionManager(), testClass);
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
