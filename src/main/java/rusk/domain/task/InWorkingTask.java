package rusk.domain.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import rusk.common.util.Now;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InWorkingTask extends Task {
    
    @JsonCreator
    InWorkingTask(@JsonProperty("id") long id, @JsonProperty("registeredDate") Date registeredDate) {
        super(id, registeredDate);
    }
    
    static InWorkingTask switchFrom(Task base) {
        InWorkingTask inWorkingTask = new InWorkingTask(base.getId(), base.getRegisteredDate());
        inWorkingTask.overwriteBy(base);
        inWorkingTask.startWorkTime();
        return inWorkingTask;
    }
    
    private void startWorkTime() {
        this.addWorkTime(WorkTime.createInWorkingTime(Now.getForStartTime()));
    }

    @Override
    public Task switchToCompletedTask() {
        Task completedTask = CompletedTask.switchFrom(this);
        this.endWorkTime(completedTask);
        return completedTask;
    }

    @Override
    public Task switchToStoppedTask() {
        Task stoppedTask = StoppedTask.switchFrom(this);
        this.endWorkTime(stoppedTask);
        return stoppedTask;
    }

    private void endWorkTime(Task task) {
        List<WorkTime> workTimes = this.getWorkTimes();
        
        workTimes.forEach(time -> {
            if (!time.hasEndTime()) {
                time.setEndTime(Now.getForEndTime());
            }
        });
        
        task.setWorkTimes(workTimes);
    }
    
    @Override
    public WorkTime getWorkTimeInWorking() {
        Optional<WorkTime> workTimeInWorking = this.getWorkTimes().stream().filter(time -> !time.hasEndTime()).findAny();
        
        if (workTimeInWorking.isPresent()) {
            return WorkTime.createInWorkingTime(workTimeInWorking.get().getStartTime());
        } else {
            throw new IllegalStateException("作業中の作業時間が存在しません。");
        }
    }

    @Override
    public Status getStatus() {
        return Status.IN_WORKING;
    }
    
    @Override
    public List<Status> getEnableToSwitchStatusList() {
        return new ArrayList<>(Arrays.asList(Status.STOPPED, Status.COMPLETE));
    }
}
