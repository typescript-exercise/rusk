package rusk.rest.system;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rusk.rest.system.db.DatabaseMigration;

@Path("initialize") @Singleton
public class RuskInitializeResource {
    private static final Logger logger = LoggerFactory.getLogger(RuskInitializeResource.class);
    
    private final DatabaseMigration dbMigration;
    
    @Inject
    public RuskInitializeResource(DatabaseMigration dbMigration) {
        this.dbMigration = dbMigration;
    }

    @GET
    public Response initialize() {
        logger.debug("initialize");
        
        this.dbMigration.migrate();
        
        return Response.status(Response.Status.OK).build();
    }
}
