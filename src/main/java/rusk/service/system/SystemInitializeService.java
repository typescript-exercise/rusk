package rusk.service.system;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rusk.system.db.DatabaseMigration;
import rusk.system.db.RuskConnectionPool;

public class SystemInitializeService {
    private static final Logger logger = LoggerFactory.getLogger(SystemInitializeService.class);
    
    private final DatabaseMigration dbMigration;
    private final RuskConnectionPool ruskConnectionPool;
    private boolean initialized = false;
    
    @Inject
    public SystemInitializeService(DatabaseMigration dbMigration, RuskConnectionPool ruskConnectionPool) {
        this.dbMigration = dbMigration;
        this.ruskConnectionPool = ruskConnectionPool;
    }
    
    synchronized public void initialize() {
        if (initialized) {
            logger.debug("system already initialized.");
            return;
        }
        
        this.dbMigration.migrate();
        this.ruskConnectionPool.initialize();
        
        initialized = true;
        
        logger.debug("complete initialize system");
    }
    
    synchronized public boolean isInitialized() {
        return this.initialized;
    }
}
