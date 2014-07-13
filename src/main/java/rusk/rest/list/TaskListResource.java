package rusk.rest.list;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import rusk.domain.list.TaskList;
import rusk.service.list.InquireTaskListService;

@Path("task-list")
public class TaskListResource {
    
    private final InquireTaskListService service;
    
    @Inject
    public TaskListResource(InquireTaskListService service) {
        this.service = service;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public TaskList inquire() {
        return this.service.inquire();
    }
}
