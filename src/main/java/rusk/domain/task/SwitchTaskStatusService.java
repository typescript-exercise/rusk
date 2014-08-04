package rusk.domain.task;

import javax.inject.Inject;

public class SwitchTaskStatusService {
    
    private final TaskRepository repository;
    
    @Inject
    public SwitchTaskStatusService(TaskRepository repository) {
        this.repository = repository;
    }

    public void switchTaskStatus(SwitchStatusForm form) {
        this.stopCurrentInWorkingTaskIfNeed(form);
        this.switchStatus(form);
    }

    private void stopCurrentInWorkingTaskIfNeed(SwitchStatusForm form) {
        Task currentInWorkingTask = this.repository.inquireTaskInWorkingWithLock();
        
        if (this.shouldStopCurrentInWorkingTask(currentInWorkingTask, form)) {
            this.stop(currentInWorkingTask);
        }
    }
    
    private boolean shouldStopCurrentInWorkingTask(Task currentInWorkingTask, SwitchStatusForm form) {
        return currentInWorkingTask != null && form.status == Status.IN_WORKING;
    }

    private void stop(Task currentInWorkingTask) {
        Task stoppedTask = Status.STOPPED.switchTaskStatus(currentInWorkingTask);
        this.repository.saveModification(stoppedTask);
    }

    private void switchStatus(SwitchStatusForm form) {
        Task task = this.repository.inquireWithLock(form.id);
        Task switchedTask = form.status.switchTaskStatus(task);
        this.repository.saveModification(switchedTask);
    }
}
