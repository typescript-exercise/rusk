package rusk;

import javax.inject.Singleton;

import org.glassfish.hk2.api.InterceptionService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rusk.rest.interceptor.RuskInterceptionService;
import rusk.rest.system.RuskSystemResource;
import rusk.system.db.DatabaseConfig;
import rusk.system.db.DatabaseMigration;
import rusk.system.db.PersistProvider;
import rusk.system.db.RuskConnection;
import rusk.system.db.RuskConnectionPool;

/**
 * HK2 の設定。
 */
public class RuskHK2Binder extends AbstractBinder {
    
    private static final Logger logger = LoggerFactory.getLogger(RuskHK2Binder.class);
    
    @Override
    protected void configure() {
        logger.debug("configure HK2.");
        
        // Interceptor
        bind(RuskInterceptionService.class).to(InterceptionService.class).in(Singleton.class);
        
        // Database
        bindAsContract(DatabaseConfig.class).in(Singleton.class);
        bindAsContract(DatabaseMigration.class).in(Singleton.class);
        bindAsContract(RuskConnectionPool.class).in(Singleton.class);
        bindAsContract(RuskConnection.class).in(RequestScoped.class);
        bindAsContract(PersistProvider.class).in(Singleton.class);
        
        // Resource
        bindAsContract(RuskSystemResource.class).in(Singleton.class);
        
        // Service
        
    }
}
