package rusk.test.db;

/**
 * テスト用の DB 接続情報
 */
public final class TestDatabaseConfig {
    /**JDBC ドライバー*/
    public static final String DRIVER = "org.hsqldb.jdbcDriver";
    /**接続 URL*/
    public static final String URL = "jdbc:hsqldb:file:testdb/rusk;shutdown=true";
    /**ユーザー*/
    public static final String USER = "SA";
    /**パスワード*/
    public static final String PASS = "";
}
