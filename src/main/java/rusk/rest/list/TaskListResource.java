package rusk.rest.list;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import rusk.domain.list.TaskList;

@Path("task-list")
public class TaskListResource {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public TaskList inquire() {
        System.out.println("TaskListResource#inquire");
        return new TaskList();
    }
}
