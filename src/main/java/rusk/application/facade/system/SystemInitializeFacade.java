package rusk.application.facade.system;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rusk.persistence.framework.RuskConnectionPool;
import rusk.persistence.migration.DatabaseMigration;

public class SystemInitializeFacade {
    private static final Logger logger = LoggerFactory.getLogger(SystemInitializeFacade.class);
    
    private final DatabaseMigration dbMigration;
    private final RuskConnectionPool ruskConnectionPool;
    private boolean initialized = false;
    
    @Inject
    public SystemInitializeFacade(DatabaseMigration dbMigration, RuskConnectionPool ruskConnectionPool) {
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
