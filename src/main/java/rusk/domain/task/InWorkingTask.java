package rusk.domain.task;

import java.util.Date;
import java.util.List;

import rusk.util.Now;

public class InWorkingTask extends Task {
    
    static InWorkingTask switchFrom(Task base) {
        InWorkingTask inWorkingTask = new InWorkingTask();
        inWorkingTask.overwriteBy(base);
        inWorkingTask.startWorkTime();
        return inWorkingTask;
    }
    
    private void startWorkTime() {
        this.addWorkTime(WorkTime.createInWorkingTime(Now.getForStartTime()));
    }
    
    static InWorkingTask build(long id, Date registeredDate) {
        return new InWorkingTask(id, registeredDate);
    }
    
    private InWorkingTask(long id, Date registeredDate) {
        super(id, registeredDate);
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
        WorkTime inWorkingTime = this.getWorkTimes().stream().filter(time -> !time.hasEndTime()).findAny().get();
        return WorkTime.createInWorkingTime(inWorkingTime.getStartTime());
    }

    @Override
    public String getStatus() {
        return "IN_WORKING";
    }
    
    @SuppressWarnings("deprecation")
    private InWorkingTask() {}
}
