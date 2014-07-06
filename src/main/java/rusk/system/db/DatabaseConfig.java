package rusk.system.db;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * データベース設定。
 * <p>
 * このクラスは、データベースファイルの保存先を定義します。
 * <p>
 * データベースファイルの保存先は、基準となるディレクトリから見て、{@code .rusk/db} と
 * なるように定義されます。
 * <p>
 * また、基準となるディレクトリは、 OS の環境変数に{@code RUSK_HOME} が設定されている場合はそのディレクトリの下が、
 * 設定されていない場合は実行ユーザーのホームディレクトリの下が選択されます。
 */
public final class DatabaseConfig {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);
    
    private final String url;
    
    /**
     * コンストラクタ
     */
    public DatabaseConfig() {
        String ruskHome = StringUtils.defaultString(System.getenv("RUSK_HOME"), System.getProperty("user.home"));
        String dbDir = ruskHome + "/.rusk/db/rusk";
        this.url = "jdbc:hsqldb:file:" + dbDir + ";shutdown=true";
        
        logger.debug("db dir = {}", dbDir);
    }

    /**
     * JDBC で DB に接続するための URL を取得します。
     * @return 接続用 URL
     */
    public String getUrl() {
        return this.url;
    }
    
    /**
     * DB 接続で使用する JDBC ドライバの FQCN を取得します。
     * @return JDBC ドライバの FQCN
     */
    public String getDriver() {
        return "org.hsqldb.jdbcDriver";
    }
    
    /**
     * DB 接続で使用するユーザー名を取得します。
     * @return ユーザー名
     */
    public String getUser() {
        return "SA";
    }
    
    /**
     * DB 接続で使用するパスワードを取得します。
     * @return パスワード
     */
    public String getPassword() {
        return "";
    }
}
