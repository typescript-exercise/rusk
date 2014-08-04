package rusk.domain.task;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
    public Status getStatus() {
        return Status.STOPPED;
    }
    
    @Override
    public List<Status> getEnableToSwitchStatusList() {
        return Arrays.asList(Status.IN_WORKING, Status.COMPLETE);
    }
    
    @SuppressWarnings("deprecation")
    private StoppedTask() {}
}
