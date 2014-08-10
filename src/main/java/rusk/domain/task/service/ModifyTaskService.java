package rusk.domain.task.service;

import javax.inject.Inject;

import rusk.domain.task.InWorkingTask;
import rusk.domain.task.Priority;
import rusk.domain.task.Task;
import rusk.domain.task.TaskRepository;
import rusk.domain.task.form.ModifyTaskForm;

public class ModifyTaskService {
    
    private final TaskRepository repository;
    private final SwitchTaskStatusService switchTaskStatusService;
    
    @Inject
    public ModifyTaskService(TaskRepository repository, SwitchTaskStatusService switchTaskStatusService) {
        this.repository = repository;
        this.switchTaskStatusService = switchTaskStatusService;
    }
    
    public void modify(Task task, ModifyTaskForm form, InWorkingTask inWorkingTask) {
        this.modifyTaskByForm(task, form);
        
        if (this.statusIsNotSwitched(task, form)) {
            this.repository.saveModification(task);
        } else {
            this.switchTaskStatusService.switchTaskStatus(task, inWorkingTask, form.status);
        }
    }

    private void modifyTaskByForm(Task task, ModifyTaskForm form) {
        task.setTitle(form.title);
        task.setDetail(form.detail);
        task.setPriority(Priority.of(form.period, form.importance));
    }

    private boolean statusIsNotSwitched(Task task, ModifyTaskForm form) {
        return task.getStatus() == form.status;
    }
}
