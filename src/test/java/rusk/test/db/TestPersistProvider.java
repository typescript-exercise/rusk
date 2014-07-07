package rusk.test.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import net.sf.persist.Persist;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import rusk.system.db.PersistProvider;

/**
 * テストデータベースに接続した{@link Persist} インスタンスを取得するためのルールクラス。
 * <p>
 * {@code @Rule} を使ってこのルールを宣言することで、このクラスはテストの前後でテストDBへの接続・切断を行います。
 * <p>
 * このルールは、データベースコネクションを autoCommit = true で生成します。
 * つまり、テスト中の変更は全てDBに反映されます。これは、エラーが発生した場合でも
 * その時点までのDBの状態が記録されるようにして、デバックを容易にするためです。
 * <p>
 * テスト時は、このクラスを各リポジトリクラスに渡すことで、リポジトリはこのクラスから{@code Persist} の
 * インスタンスを取得し、テストDBへの接続を行えるようになります。
 */
public class TestPersistProvider implements PersistProvider, TestRule {
    
    private Connection connection;

    @Override
    public Persist getPersist() {
        if (this.connection == null) {
            throw new IllegalStateException("Database connection is not opened. This class must be used as TestRule.");
        }
        
        return new Persist(this.connection);
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    openConnection();
                    base.evaluate();
                } finally {
                    closeConnection();
                }
            }
        };
    }

    private void openConnection() {
        try {
            Class.forName(TestDatabaseConfig.DRIVER);
            this.connection = DriverManager.getConnection(TestDatabaseConfig.URL, TestDatabaseConfig.USER, TestDatabaseConfig.PASS);
            this.connection.setAutoCommit(true);
        } catch (Exception e) {
            throw new RuntimeException("failed to connect test db.", e);
        }
    }
    
    private void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("failed to close test db.", e);
        }
    }
}
