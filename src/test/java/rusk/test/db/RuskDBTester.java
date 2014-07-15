package rusk.test.db;

import org.dbunit.dataset.IDataSet;

import jp.classmethod.testing.database.DbUnitTester;
import jp.classmethod.testing.database.JdbcDatabaseConnectionManager;
import jp.classmethod.testing.database.YamlDataSet;

/**
 * Rusk 開発用の DbUnitTester
 */
public class RuskDBTester extends DbUnitTester {
    
    private final Class<?> testClass;
    
    public RuskDBTester(JdbcDatabaseConnectionManager manager, Class<?> testClass) {
        super(manager);
        this.testClass = testClass;
    }
    
    public RuskDBTester() {
        this(new RuskJdbcDatabaseConnectionManager(), null);
    }

    public RuskDBTester(Class<?> testClass) {
        this(new RuskJdbcDatabaseConnectionManager(), testClass);
    }

    private static class RuskJdbcDatabaseConnectionManager extends JdbcDatabaseConnectionManager {
        RuskJdbcDatabaseConnectionManager() {
            super(TestDatabaseConfig.DRIVER, TestDatabaseConfig.URL);
            super.username = TestDatabaseConfig.USER;
            super.password = TestDatabaseConfig.PASS;
        }
    }
    /**
     * 指定したリソース（yaml ファイル）を {@link IDataSet} で読み込みます。
     * 
     * @param resource 読み込みリソース
     * @return 読み込んだデータ
     */
    public IDataSet loadDataSet(String resource) {
        return YamlDataSet.load(this.testClass.getResourceAsStream(resource));
    }
}
