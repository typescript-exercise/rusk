package rusk.domain.task;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.Validate;

import rusk.common.util.Now;

public class CompletedTask extends Task {
    
    private Date completedDate;

    static CompletedTask switchFrom(Task task) {
        CompletedTask completedTask = new CompletedTask();
        completedTask.overwriteBy(task);
        completedTask.setCompletedDate(Now.getForCompletedDate());
        return completedTask;
    }
    
    CompletedTask(long id, Date registeredDate, Date completedDate) {
        super(id, registeredDate);
        this.setCompletedDate(completedDate);
    }
    
    private void setCompletedDate(Date completedDate) {
        Validate.notNull(completedDate, "完了日時は必須です");
        this.completedDate = new Date(completedDate.getTime());
    }

    @Override
    public InWorkingTask switchToInWorkingTask() {
        InWorkingTask inWorkingTask = InWorkingTask.switchFrom(this);
        return inWorkingTask;
    }

    @Override
    public boolean isCompleted() {
        return true;
    }

    @Override
    public Status getStatus() {
        return Status.COMPLETE;
    }
    
    @Override
    public List<Status> getEnableToSwitchStatusList() {
        return Arrays.asList(Status.IN_WORKING);
    }

    @Override
    public Date getCompletedDate() {
        return new Date(this.completedDate.getTime());
    }

    @SuppressWarnings("deprecation")
    private CompletedTask() {}
}
