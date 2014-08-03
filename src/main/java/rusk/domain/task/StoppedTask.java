package rusk.domain.task;

import java.util.Date;

public class StoppedTask extends Task {

    static StoppedTask switchFrom(Task src) {
        StoppedTask stoppedTask = new StoppedTask();
        stoppedTask.overwriteBy(src);
        return stoppedTask;
    }
    
    StoppedTask(long id, Date registeredDate) {
        super(id, registeredDate);
    }

    @Override
    public Task switchToCompletedTask() {
        return CompletedTask.switchFrom(this);
    }

    @Override
    public Task switchToInWorkingTask() {
        return InWorkingTask.switchFrom(this);
    }

    @Override
    public String getStatus() {
        return "STOPPED";
    }
    
    @SuppressWarnings("deprecation")
    private StoppedTask() {}
}
