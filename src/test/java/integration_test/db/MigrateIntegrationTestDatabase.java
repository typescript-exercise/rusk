package integration_test.db;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * テスト用のデータベースをマイグレーションするためのクラス。
 * <p>
 * データベースの定義が変更された場合は、このクラスを手動で実行してからテストを実行してください。
 */
public class MigrateIntegrationTestDatabase {
    
    private static final Logger logger = LoggerFactory.getLogger(MigrateIntegrationTestDatabase.class);
    
    public static void main(String[] args) {
        TestDatabaseConfig config = new TestDatabaseConfig();
        
        Flyway flyway = new Flyway();
        flyway.setDataSource(config.getUrl(), config.getUser(), config.getPassword());
        flyway.clean();
        flyway.migrate();
        
        logger.info("success to migrate integration test database.");
    }
}
