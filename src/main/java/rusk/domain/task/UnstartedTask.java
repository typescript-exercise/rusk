package rusk.domain.task;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class UnstartedTask extends Task {
    
    UnstartedTask(Date registeredDate) {
        super(registeredDate);
    }

    UnstartedTask(long id, Date registereddate) {
        super(id, registereddate);
    }

    @Override
    public Task switchToCompletedTask() {
        return CompletedTask.switchFrom(this);
    }

    @Override
    public Task switchToInWorkingTask() {
        InWorkingTask inWorkingTask = InWorkingTask.switchFrom(this);
        return inWorkingTask;
    }

    @Override
    public Status getStatus() {
        return Status.UNSTARTED;
    }
    
    @Override
    public List<Status> getEnableToSwitchStatusList() {
        return Arrays.asList(Status.IN_WORKING, Status.COMPLETE);
    }
}
