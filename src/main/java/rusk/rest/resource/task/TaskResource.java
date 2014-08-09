package rusk.rest.resource.task;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rusk.application.facade.task.TaskFacade;
import rusk.domain.task.Task;
import rusk.domain.task.form.ModifyTaskForm;
import rusk.domain.task.form.RegisterTaskForm;
import rusk.domain.task.form.SwitchStatusForm;
import rusk.rest.RuskConfig;

@Path("task")
public class TaskResource {
    private static final Logger logger = LoggerFactory.getLogger(TaskResource.class);
    
    private final TaskFacade taskService;
    
    @Inject
    public TaskResource(TaskFacade taskService) {
        this.taskService = taskService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(RegisterTaskForm form) throws URISyntaxException {
        logger.debug("RegisterTaskForm = {}", form);
        
        Task registerdTask = this.taskService.register(form);
        
        URI uri = RuskConfig.resource(TaskResource.class).path("{id}").build(registerdTask.getId());
        
        return Response.created(uri).build();
    }
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Task inquire(@PathParam("id") long id) {
        return this.taskService.inquire(id);
    }
    
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") long removeTargetTaskId) {
        this.taskService.remove(removeTargetTaskId);
    }
    
    @PUT
    @Path("{id}/status")
    public void switchStatus(@PathParam("id") long id, SwitchStatusForm form) {
        form.id = id;
        this.taskService.switchTaskStatus(form);
    }
    
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void modify(@PathParam("id") long id, ModifyTaskForm form) {
        form.id = id;
        this.taskService.modify(form);
    }
}
