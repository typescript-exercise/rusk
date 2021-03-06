package rusk.persistence.framework;

import java.sql.Connection;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * データベース接続のコネクションプール。
 */
public class RuskConnectionPool {
    private static final Logger logger = LoggerFactory.getLogger(RuskConnectionPool.class);
    
    private DataSource dataSource;
    private final DatabaseConfig databaseConfig;
    private boolean isInitialized;
    
    @Inject
    public RuskConnectionPool(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    /**
     * コネクションプールを生成する。
     */
    synchronized public void initialize() {
        if (this.isInitialized) {
            return;
        }
        
        logger.debug("initialize datasource.");
        
        BasicDataSource dataSource = new BasicDataSource();
        
        dataSource.setDriverClassName(databaseConfig.getDriver());
        dataSource.setUrl(databaseConfig.getUrl());
        dataSource.setUsername(databaseConfig.getUser());
        dataSource.setPassword(databaseConfig.getPassword());
        dataSource.setDefaultAutoCommit(false);
        dataSource.setMaxActive(3);
        dataSource.setMaxIdle(3);
        
        this.dataSource = dataSource;
        
        this.isInitialized = true;
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
