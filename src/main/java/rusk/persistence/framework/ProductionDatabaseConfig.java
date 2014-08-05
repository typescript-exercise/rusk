package rusk.persistence.framework;

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
public final class ProductionDatabaseConfig implements DatabaseConfig {
    private static final Logger logger = LoggerFactory.getLogger(ProductionDatabaseConfig.class);
    
    private final String url;
    
    /**
     * コンストラクタ
     */
    public ProductionDatabaseConfig() {
        String ruskHome = StringUtils.defaultString(System.getenv("RUSK_HOME"), System.getProperty("user.home"));
        String dbDir = ruskHome + "/.rusk/db/rusk";
        this.url = "jdbc:hsqldb:file:" + dbDir + ";shutdown=true";
        
        logger.debug("db dir = {}", dbDir);
    }

    @Override
    public String getUrl() {
        return this.url;
    }
    
    @Override
    public String getDriver() {
        return "org.hsqldb.jdbcDriver";
    }
    
    @Override
    public String getUser() {
        return "SA";
    }
    
    @Override
    public String getPassword() {
        return "";
    }
}
