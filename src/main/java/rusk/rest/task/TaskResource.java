package rusk.rest.task;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import rusk.rest.RuskConfig;

@Path("task")
public class TaskResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(RegisterTaskForm form) throws URISyntaxException {
        URI uri = RuskConfig.resource(TaskResource.class).path("{id}").build(1);
        return Response.created(uri).build();
    }
}
