package test.db;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * テスト用のデータベースをマイグレーションするためのクラス。
 * <p>
 * データベースの定義が変更された場合は、このクラスを手動で実行してからテストを実行してください。
 */
public class MigrateUnitTestDatabase {
    
    private static final Logger logger = LoggerFactory.getLogger(MigrateUnitTestDatabase.class);
    
    public static void main(String[] args) {
        Flyway flyway = new Flyway();
        flyway.setDataSource(TestDatabaseConfig.URL, TestDatabaseConfig.USER, TestDatabaseConfig.PASS);
        flyway.clean();
        flyway.migrate();
        
        logger.info("success to migrate unit test database.");
    }
}
