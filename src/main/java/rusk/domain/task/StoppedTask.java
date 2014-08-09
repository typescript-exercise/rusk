package rusk.domain.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StoppedTask extends Task {
    
    @JsonCreator
    StoppedTask(@JsonProperty("id") long id, @JsonProperty("registeredDate") Date registeredDate) {
        super(id, registeredDate);
    }

    static StoppedTask switchFrom(Task src) {
        StoppedTask stoppedTask = new StoppedTask(src.getId(), src.getRegisteredDate());
        stoppedTask.overwriteBy(src);
        return stoppedTask;
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
        return new ArrayList<>(Arrays.asList(Status.IN_WORKING, Status.COMPLETE));
    }
}
