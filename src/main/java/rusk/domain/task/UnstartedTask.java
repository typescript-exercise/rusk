package rusk.domain.task;

import java.util.Date;

public class UnstartedTask extends Task {
    
    UnstartedTask(Date registeredDate) {
        super(registeredDate);
    }

    UnstartedTask(long id, Date registereddate) {
        super(id, registereddate);
    }

    @Override
    public Task switchToCompletedTask() {
        return CompletedTask.createBy(this);
    }

    @Override
    public Task switchToInWorkingTask() {
        InWorkingTask inWorkingTask = InWorkingTask.createBy(this);
        return inWorkingTask;
    }

    @Override
    public String getStatus() {
        return "UNSTARTED";
    }
}
