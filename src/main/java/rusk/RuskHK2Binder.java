package rusk;

import javax.inject.Singleton;

import org.glassfish.hk2.api.InterceptionService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rusk.domain.task.TaskRepository;
import rusk.interceptor.RuskInterceptionService;
import rusk.persistence.task.TaskRepositoryImpl;
import rusk.rest.list.TaskListResource;
import rusk.rest.task.TaskResource;
import rusk.service.list.InquireTaskListService;
import rusk.service.system.SystemInitializeService;
import rusk.service.task.TaskService;
import rusk.system.db.DatabaseConfig;
import rusk.system.db.DatabaseMigration;
import rusk.system.db.HK2PersistProvider;
import rusk.system.db.PersistProvider;
import rusk.system.db.ProductionDatabaseConfig;
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
        this.bindComponents(ProductionDatabaseConfig.class);
    }
    
    /**
     * Rusk アプリの動作に必要なコンポーネントの登録を行う。
     * 
     * @param databaseConfigClass データベース接続設定用のクラス
     */
    protected void bindComponents(Class<? extends DatabaseConfig> databaseConfigClass) {
        // interceptor
        bind(RuskInterceptionService.class).to(InterceptionService.class).in(Singleton.class);

        // database
        bind(databaseConfigClass).to(DatabaseConfig.class).in(Singleton.class);
        bindAsContract(DatabaseMigration.class).in(Singleton.class);
        bindAsContract(RuskConnectionPool.class).in(Singleton.class);
        bindAsContract(RuskConnection.class).in(RequestScoped.class);
        bind(HK2PersistProvider.class).to(PersistProvider.class).in(Singleton.class);
        
        // service
        bindAsContract(InquireTaskListService.class).in(Singleton.class);
        bindAsContract(SystemInitializeService.class).in(Singleton.class);
        bindAsContract(TaskService.class).in(Singleton.class);
        
        // repository
        bind(TaskRepositoryImpl.class).to(TaskRepository.class).in(Singleton.class);
        
        // resource
        bindAsContract(TaskListResource.class).in(Singleton.class);
        bindAsContract(TaskResource.class).in(Singleton.class);
    }
}
