package rusk.domain.task.service;

import javax.inject.Inject;

import rusk.domain.task.InWorkingTask;
import rusk.domain.task.Status;
import rusk.domain.task.Task;
import rusk.domain.task.TaskRepository;

public class SwitchTaskStatusService {
    
    private final TaskRepository repository;
    
    @Inject
    public SwitchTaskStatusService(TaskRepository repository) {
        this.repository = repository;
    }

    public void switchTaskStatus(Task targetTask, InWorkingTask inWorkingTask, Status toStatus) {
        if (this.shouldStopInWorkingTask(inWorkingTask, toStatus)) {
            this.switchStatus(inWorkingTask, Status.STOPPED);
        }
        
        this.switchStatus(targetTask, toStatus);
    }
    
    private boolean shouldStopInWorkingTask(InWorkingTask inWorkingTask, Status toStatus) {
        return inWorkingTask != null && toStatus == Status.IN_WORKING;
    }

    private void switchStatus(Task fromTask, Status toStatus) {
        Task switchedTask = toStatus.switchTaskStatus(fromTask);
        this.repository.saveModification(switchedTask);
    }
}
