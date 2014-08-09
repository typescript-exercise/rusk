package rusk.domain.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UnstartedTask extends Task {
    
    UnstartedTask(Date registeredDate) {
        super(registeredDate);
    }

    @JsonCreator
    UnstartedTask(@JsonProperty("id") long id, @JsonProperty("registeredDate") Date registeredDate) {
        super(id, registeredDate);
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
        return new ArrayList<>(Arrays.asList(Status.IN_WORKING, Status.COMPLETE));
    }
    
}
