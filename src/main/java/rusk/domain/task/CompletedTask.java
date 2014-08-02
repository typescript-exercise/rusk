package rusk.domain.task;

import java.util.Date;

import rusk.util.Now;

public class CompletedTask extends Task {

    static CompletedTask createBy(Task task) {
        CompletedTask completedTask = new CompletedTask();
        completedTask.overwriteBy(task);
        completedTask.setCompletedDate(Now.get());
        return completedTask;
    }
    
    CompletedTask(long id, Date registeredDate, Date completedDate) {
        super(id, registeredDate, completedDate);
    }

    @Override
    public InWorkingTask switchToInWorkingTask() {
        InWorkingTask inWorkingTask = InWorkingTask.createBy(this);
        inWorkingTask.setCompletedDate(null);
        return inWorkingTask;
    }

    @Override
    public boolean isCompleted() {
        return true;
    }

    @Override
    public String getStatus() {
        return "COMPLETE";
    }
    
    @SuppressWarnings("deprecation")
    private CompletedTask() {}
}
