package rusk.system.db;

public interface DatabaseConfig {

    /**
     * JDBC で DB に接続するための URL を取得します。
     * @return 接続用 URL
     */
    String getUrl();
    
    /**
     * DB 接続で使用する JDBC ドライバの FQCN を取得します。
     * @return JDBC ドライバの FQCN
     */
    String getDriver();
    
    /**
     * DB 接続で使用するユーザー名を取得します。
     * @return ユーザー名
     */
    String getUser();
    
    /**
     * DB 接続で使用するパスワードを取得します。
     * @return パスワード
     */
    String getPassword();
}
