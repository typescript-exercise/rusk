package rusk.rest.task;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import rusk.domain.task.TaskList;
import rusk.service.task.TaskService;

@Path("task-list")
public class TaskListResource {
    
    private final TaskService service;
    
    @Inject
    public TaskListResource(TaskService service) {
        this.service = service;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public TaskList inquire() {
        return this.service.inquireTaskList();
    }
}
