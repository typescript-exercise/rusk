package rusk.rest.task;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rusk.domain.task.Task;
import rusk.rest.RuskConfig;
import rusk.service.task.RegisterTaskService;

@Path("task")
public class TaskResource {
    private static final Logger logger = LoggerFactory.getLogger(TaskResource.class);
    
    private final RegisterTaskService service;
    
    @Inject
    public TaskResource(RegisterTaskService service) {
        this.service = service;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(RegisterTaskForm form) throws URISyntaxException {
        logger.debug("RegisterTaskForm = {}", form);
        
        Task registerdTask = this.service.register(form);
        
        URI uri = RuskConfig.resource(TaskResource.class).path("{id}").build(registerdTask.getId());
        
        return Response.created(uri).build();
    }
}
