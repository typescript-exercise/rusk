package test.db;

import integration_test.db.MigrateIntegrationTestDatabase;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rusk.persistence.framework.DatabaseConfig;
import rusk.persistence.framework.ProductionDatabaseConfig;

public class MigrateDevelopDatabases {

    private static final Logger logger = LoggerFactory.getLogger(MigrateDevelopDatabases.class);
    
    public static void main(String[] args) {
        DatabaseConfig config = new ProductionDatabaseConfig();
        
        Flyway flyway = new Flyway();
        flyway.setDataSource(config.getUrl(), config.getUser(), config.getPassword());
        flyway.clean();
        flyway.migrate();
        
        logger.info("success to migrate develop database.");
        
        MigrateUnitTestDatabase.main(null);
        MigrateIntegrationTestDatabase.main(null);
    }
}
