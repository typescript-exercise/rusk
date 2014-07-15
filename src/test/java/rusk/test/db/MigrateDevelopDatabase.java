package rusk.test.db;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rusk.system.db.DatabaseConfig;
import rusk.system.db.ProductionDatabaseConfig;

public class MigrateDevelopDatabase {

    private static final Logger logger = LoggerFactory.getLogger(MigrateDevelopDatabase.class);
    
    public static void main(String[] args) {
        DatabaseConfig config = new ProductionDatabaseConfig();
        
        Flyway flyway = new Flyway();
        flyway.setDataSource(config.getUrl(), config.getUser(), config.getPassword());
        flyway.clean();
        flyway.migrate();
        
        logger.info("success to migrate develop database.");
    }
}
