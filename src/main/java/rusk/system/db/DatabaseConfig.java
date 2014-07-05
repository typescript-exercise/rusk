package rusk.system.db;

/**
 * データベース設定。
 */
public final class DatabaseConfig {

    /**JDBC ドライバー*/
    public static final String DRIVER = "org.hsqldb.jdbcDriver";
    /**データベース URL*/
    public static final String URL = "jdbc:hsqldb:file:db/rusk;shutdown=true";
    /**接続ユーザー名*/
    public static final String USER = "SA";
    /**接続パスワード*/
    public static final String PASSWORD = "";
    
    private DatabaseConfig() {}
}
