package rusk.system.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * リクエストごとのデータベースコネクションを持つクラス。
 */
public class RuskConnection {
    private static final Logger logger = LoggerFactory.getLogger(RuskConnection.class);
    
    private final Connection connection;
    
    /**
     * コネクションプールからコネクションを取得してインスタンスを生成する。
     * 
     * @param connectionPool コネクションプール
     */
    @Inject
    public RuskConnection(RuskConnectionPool connectionPool) {
        this.connection = connectionPool.getConnection();
    }
    
    /**
     * トランザクションを開始する。
     */
    public void beginTransaction() {
        try {
            this.connection.setAutoCommit(false);
            logger.debug("begin transaction.");
        } catch (SQLException e) {
            logger.error("failed to begin transaction.", e);
            throw new RuntimeException(e);
        }
    }
    
    /**
     * トランザクションをコミットする。
     */
    public void commit() {
        try {
            this.connection.commit();
            logger.debug("commit transaction.");
        } catch (SQLException e) {
            logger.error("failed to commit transaction.", e);
            throw new RuntimeException(e);
        }
    }
    
    /**
     * トランザクションをロールバックする。
     */
    public void rollback() {
        try {
            this.connection.rollback();
            logger.debug("rollback transaction.");
        } catch (SQLException e) {
            logger.error("failed to rollback transaction.", e);
            throw new RuntimeException(e);
        }
    }
    
    /**
     * コネクションを取得する。
     * 
     * @return コネクション
     */
    public Connection getConnection() {
        return this.connection;
    }
    
    /**
     * コネクションを解放する。
     */
    @PreDestroy
    public void release() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            logger.error("failed to close connection.", e);
            throw new RuntimeException(e);
        }
    }
}
