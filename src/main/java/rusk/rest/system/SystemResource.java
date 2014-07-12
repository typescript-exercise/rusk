package rusk.rest.system;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rusk.system.db.DatabaseMigration;
import rusk.system.db.RuskConnectionPool;

/**
 * Rusk の初期化処理を提供するリソースクラス。
 */
@Path("system")
public class SystemResource {
    private static final Logger logger = LoggerFactory.getLogger(SystemResource.class);
    
    private final DatabaseMigration dbMigration;
    private final RuskConnectionPool ruskConnectionPool;
    
    @Inject
    public SystemResource(DatabaseMigration dbMigration, RuskConnectionPool ruskConnectionPool) {
        this.dbMigration = dbMigration;
        this.ruskConnectionPool = ruskConnectionPool;
    }

    @POST
    public Response initialize() {
        logger.debug("initialize system");
        
        this.dbMigration.migrate();
        this.ruskConnectionPool.initialize();
        
        return Response.status(Response.Status.OK).build();
    }
}
