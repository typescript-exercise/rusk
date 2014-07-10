package rusk.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import rusk.domain.list.TaskList;
import rusk.service.InquireTaskListService;

@Path("task-list")
public class TaskListResource {
    
    private InquireTaskListService service;
    
    @Inject
    public TaskListResource(InquireTaskListService service) {
        this.service = service;
    }
    
    @GET
    public TaskList inquire() {
        System.out.println("inquire task list");
        return null;
    }
}
