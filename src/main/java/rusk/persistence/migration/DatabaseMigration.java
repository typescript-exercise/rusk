package rusk.persistence.migration;

import javax.inject.Inject;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rusk.persistence.framework.DatabaseConfig;

/**
 * データベースのマイグレーションを行うクラス。
 */
public class DatabaseMigration {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseMigration.class);
    
    private final DatabaseConfig databaseConfig;
    
    @Inject
    public DatabaseMigration(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    /**
     * マイグレーションを実行する。
     */
    public void migrate() {
        try {
            Flyway flyway = new Flyway();
            flyway.setDataSource(this.databaseConfig.getUrl(), this.databaseConfig.getUser(), this.databaseConfig.getPassword());
            flyway.migrate();
            logger.debug("success to migrate database.");
        } catch (FlywayException e) {
            logger.error("failed to migrate database.", e);
            throw e;
        }
    }
}
