package rusk.domain.task.service;

import javax.inject.Inject;

import rusk.domain.task.InWorkingTask;
import rusk.domain.task.Priority;
import rusk.domain.task.Task;
import rusk.domain.task.form.ModifyTaskForm;

public class ModifyTaskService {
    
    private final SwitchTaskStatusService switchTaskStatusService;
    
    @Inject
    public ModifyTaskService(SwitchTaskStatusService switchTaskStatusService) {
        this.switchTaskStatusService = switchTaskStatusService;
    }
    
    public void modify(Task task, ModifyTaskForm form, InWorkingTask inWorkingTask) {
        task.setTitle(form.title);
        task.setDetail(form.detail);
        task.setPriority(Priority.of(form.period, form.importance));
        
        this.switchTaskStatusService.switchTaskStatus(task, inWorkingTask, form.status);
    }
}
