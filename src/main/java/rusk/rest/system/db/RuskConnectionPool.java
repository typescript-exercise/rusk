package rusk.rest.system.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.inject.Singleton;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * データベース接続のコネクションプール。
 */
@Singleton
public class RuskConnectionPool {
    private static final Logger logger = LoggerFactory.getLogger(RuskConnectionPool.class);
    
    private final DataSource dataSource;
    
    /**
     * コネクションプールを作成する。
     */
    public RuskConnectionPool() {
        logger.debug("construct datasource.");
        
        BasicDataSource dataSource = new BasicDataSource();
        
        dataSource.setDriverClassName(DatabaseConfig.DRIVER);
        dataSource.setUrl(DatabaseConfig.URL);
        dataSource.setUsername(DatabaseConfig.USER);
        dataSource.setPassword(DatabaseConfig.PASSWORD);
        dataSource.setDefaultAutoCommit(false);
        dataSource.setMaxActive(3);
        dataSource.setMaxIdle(3);
        
        this.dataSource = dataSource;
    }
    
    /**
     * コネクションプールからコネクションを取得する。
     * 
     * @return コネクション
     */
    public Connection getConnection() {
        try {
            return this.dataSource.getConnection();
        } catch (SQLException e) {
            logger.error("failed to get connection.", e);
            throw new RuntimeException(e);
        }
    }
}
