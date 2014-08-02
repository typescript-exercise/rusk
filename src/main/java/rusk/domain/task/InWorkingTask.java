package rusk.domain.task;

import java.util.Date;

import rusk.util.Now;

public class InWorkingTask extends Task {

    static InWorkingTask createBy(Task base) {
        InWorkingTask inWorkingTask = new InWorkingTask();
        inWorkingTask.overwriteBy(base);
        inWorkingTask.startWorkTime();
        return inWorkingTask;
    }
    
    private void startWorkTime() {
        WorkTime inWorkingTime = WorkTime.createInWorkingTime(Now.get());
        this.addWorkTime(inWorkingTime);
    }
    
    InWorkingTask(long id, Date registeredDate) {
        super(id, registeredDate);
        
        WorkTime workTime = WorkTime.createInWorkingTime(Now.get());
        this.addWorkTime(workTime);
    }

    @Override
    public Task switchToCompletedTask() {
        this.endWorktime();
        return CompletedTask.createBy(this);
    }
    
    private void endWorktime() {
        WorkTime timeInWorking = this.getWorkTimeInWorking();
        timeInWorking.setEndTime(Now.get());
    }

    @Override
    public Task switchToStoppedTask() {
        this.endWorktime();
        return StoppedTask.createBy(this);
    }

    @Override
    public String getStatus() {
        return "IN_WORKING";
    }
    
    @SuppressWarnings("deprecation")
    private InWorkingTask() {}
}
