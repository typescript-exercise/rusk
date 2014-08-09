package rusk;

import javax.inject.Singleton;

import org.glassfish.hk2.api.InterceptionService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rusk.application.facade.system.SystemInitializeFacade;
import rusk.application.facade.task.TaskFacade;
import rusk.application.interceptor.RuskInterceptionService;
import rusk.domain.task.TaskRepository;
import rusk.domain.task.service.InquireTaskListService;
import rusk.domain.task.service.ModifyTaskService;
import rusk.domain.task.service.SwitchTaskStatusService;
import rusk.persistence.framework.DatabaseConfig;
import rusk.persistence.framework.HK2PersistProvider;
import rusk.persistence.framework.PersistProvider;
import rusk.persistence.framework.ProductionDatabaseConfig;
import rusk.persistence.framework.RuskConnection;
import rusk.persistence.framework.RuskConnectionPool;
import rusk.persistence.migration.DatabaseMigration;
import rusk.persistence.task.TaskRepositoryImpl;
import rusk.rest.resource.task.TaskListResource;
import rusk.rest.resource.task.TaskResource;

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
        
        // facade
        bindAsContract(SystemInitializeFacade.class).in(Singleton.class);
        bindAsContract(TaskFacade.class).in(Singleton.class);
        
        // service
        bindAsContract(InquireTaskListService.class).in(Singleton.class);
        bindAsContract(SwitchTaskStatusService.class).in(Singleton.class);
        bindAsContract(ModifyTaskService.class).in(Singleton.class);
        
        // repository
        bind(TaskRepositoryImpl.class).to(TaskRepository.class).in(Singleton.class);
        
        // resource
        bindAsContract(TaskListResource.class).in(Singleton.class);
        bindAsContract(TaskResource.class).in(Singleton.class);
    }
}
