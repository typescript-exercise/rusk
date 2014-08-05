package rusk.rest.resource.task;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import rusk.application.facade.task.TaskFacade;
import rusk.domain.task.TaskList;

@Path("task-list")
public class TaskListResource {
    
    private final TaskFacade service;
    
    @Inject
    public TaskListResource(TaskFacade service) {
        this.service = service;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public TaskList inquire() {
        return this.service.inquireTaskList();
    }
}
