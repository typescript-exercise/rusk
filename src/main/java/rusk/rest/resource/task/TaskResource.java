package rusk.rest.resource.task;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

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

import rusk.application.facade.task.DailyHistoryDto;
import rusk.application.facade.task.TaskFacade;
import rusk.domain.task.Task;
import rusk.domain.task.form.ModifyTaskForm;
import rusk.domain.task.form.ModifyWorkTimeForm;
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
    
    @POST
    @Path("{id}/work-time")
    @Produces(MediaType.APPLICATION_JSON)
    public void registerWorkTime(@PathParam("id") long id, ModifyWorkTimeForm form) {
        form.taskId = id;
        this.taskService.registerWorkTime(form);
    }
    
    @PUT
    @Path("{taskId}/work-time/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void modifyWorkTime(@PathParam("taskId") long taskId, @PathParam("id") long workTimeId, ModifyWorkTimeForm form) {
        form.taskId = taskId;
        form.workTimeId = workTimeId;
        this.taskService.modifyWorkTime(form);
    }
    
    @DELETE
    @Path("{taskId}/work-time/{id}")
    public void removeWorkTime(@PathParam("taskId") long taskId, @PathParam("id") long workTimeId) {
        ModifyWorkTimeForm form = new ModifyWorkTimeForm();
        form.taskId = taskId;
        form.workTimeId = workTimeId;
        this.taskService.removeWorkTime(form);
    }
    
    @GET
    @Path("daily/{date}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DailyHistoryDto> inquireDailyHistory(@PathParam("date") String date) {
        List<DailyHistoryDto> dummy = new ArrayList<>();
        dummy.add(dto(1L, "タスク１", 1.5));
        dummy.add(dto(2L, "タスク２", 1.25));
        
        return dummy;
    }
    
    private static DailyHistoryDto dto(long id, String title, double workTimeSummary) {
        DailyHistoryDto dto = new DailyHistoryDto();
        dto.id = id;
        dto.title = title;
        dto.workTimeSummary = workTimeSummary;
        return dto;
    }
}
