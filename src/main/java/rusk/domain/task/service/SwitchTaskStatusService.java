package rusk.domain.task.service;

import java.util.Date;

import javax.inject.Inject;

import rusk.domain.ConcurrentUpdateException;
import rusk.domain.task.Status;
import rusk.domain.task.Task;
import rusk.domain.task.TaskRepository;
import rusk.domain.task.form.SwitchStatusForm;

public class SwitchTaskStatusService {
    
    private final TaskRepository repository;
    
    @Inject
    public SwitchTaskStatusService(TaskRepository repository) {
        this.repository = repository;
    }

    public void switchTaskStatus(SwitchStatusForm form) {
        Task currentInWorkingTask = this.repository.inquireTaskInWorkingWithLock();
        this.verifyConcurrentUpdateForInWorkingTask(currentInWorkingTask, form);
        this.stopCurrentInWorkingTaskIfNeed(currentInWorkingTask, form);
        
        Task targetTask = this.repository.inquireWithLock(form.id);
        this.verifyConcurrentUpdate(targetTask, form.getLastUpdateDate());
        this.switchStatus(targetTask, form.status, form.lastUpdateDate);
    }

    private void verifyConcurrentUpdateForInWorkingTask(Task currentInWorkingTask, SwitchStatusForm form) {
        if (oneExistsButOtherNotExistsForInWorkingTask(currentInWorkingTask, form)) {
            throw new ConcurrentUpdateException();
        }
        
        if (currentInWorkingTask == null) {
            return; // 検証すべき作業中タスクは存在しない
        }
        
        boolean alreadySwitchedToOtherStatus = currentInWorkingTask.getId() != form.inWorkingTaskId;
        
        if (alreadySwitchedToOtherStatus) {
            throw new ConcurrentUpdateException();
        }
        
        this.verifyConcurrentUpdate(currentInWorkingTask, form.getInWorkingTaskLastUpdateDate());
    }
    
    private boolean oneExistsButOtherNotExistsForInWorkingTask(Task currentInWorkingTask, SwitchStatusForm form) {
        return currentInWorkingTask == null ? form.hasInWorkingParameter() : !form.hasInWorkingParameter();
    }
    
    private void verifyConcurrentUpdate(Task storedTask, Date lastUpdateDate) {
        if (lastUpdateDate.getTime() < storedTask.getUpdateDate().getTime()) {
            throw new ConcurrentUpdateException();
        }
    }

    private void stopCurrentInWorkingTaskIfNeed(Task currentInWorkingTask, SwitchStatusForm form) {
        if (this.shouldStopCurrentInWorkingTask(currentInWorkingTask, form.status)) {
            this.stop(currentInWorkingTask, form.inWorkingTaskLastUpdateDate);
        }
    }
    
    private boolean shouldStopCurrentInWorkingTask(Task currentInWorkingTask, Status toStatus) {
        return currentInWorkingTask != null && toStatus == Status.IN_WORKING;
    }

    private void stop(Task task, Date lastUpdateDate) {
        this.switchStatus(task, Status.STOPPED, lastUpdateDate);
    }

    private void switchStatus(Task task, Status toStatus, Date lastUpdateDate) {
        Task switchedTask = toStatus.switchTaskStatus(task);
        this.repository.saveModification(switchedTask, lastUpdateDate);
    }
}
