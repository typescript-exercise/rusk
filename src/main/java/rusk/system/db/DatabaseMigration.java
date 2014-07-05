package rusk.system.db;

import javax.inject.Singleton;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * データベースのマイグレーションを行うクラス。
 */
@Singleton
public class DatabaseMigration {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseMigration.class);
    
    /**
     * マイグレーションを実行する。
     */
    public void migrate() {
        try {
            Flyway flyway = new Flyway();
            flyway.setDataSource(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
            flyway.migrate();
            logger.debug("success to migrate database.");
        } catch (FlywayException e) {
            logger.error("failed to migrate database.", e);
            throw e;
        }
    }
}
