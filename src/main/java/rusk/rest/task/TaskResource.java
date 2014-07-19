package rusk.rest.task;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rusk.domain.task.Task;
import rusk.rest.RuskConfig;
import rusk.service.task.InquireTaskService;
import rusk.service.task.RegisterTaskService;

@Path("task")
public class TaskResource {
    private static final Logger logger = LoggerFactory.getLogger(TaskResource.class);
    
    private final RegisterTaskService registerService;
    private final InquireTaskService inquireService;
    
    @Inject
    public TaskResource(RegisterTaskService registerService, InquireTaskService inquireService) {
        this.registerService = registerService;
        this.inquireService = inquireService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(RegisterTaskForm form) throws URISyntaxException {
        logger.debug("RegisterTaskForm = {}", form);
        
        Task registerdTask = this.registerService.register(form);
        
        URI uri = RuskConfig.resource(TaskResource.class).path("{id}").build(registerdTask.getId());
        
        return Response.created(uri).build();
    }
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Task inquire(@PathParam("id") long id) {
        return this.inquireService.inquire(id);
    }
}
