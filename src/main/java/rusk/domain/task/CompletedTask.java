package rusk.domain.task;

import java.util.Date;

import org.apache.commons.lang3.Validate;

import rusk.util.Now;

public class CompletedTask extends Task {
    
    private Date completedDate;

    static CompletedTask createBy(Task task) {
        CompletedTask completedTask = new CompletedTask();
        completedTask.overwriteBy(task);
        completedTask.setCompletedDate(Now.get());
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
        InWorkingTask inWorkingTask = InWorkingTask.createBy(this);
        return inWorkingTask;
    }

    @Override
    public boolean isCompleted() {
        return true;
    }

    @Override
    public String getStatus() {
        return "COMPLETE";
    }
    
    public Date getCompletedDate() {
        return new Date(this.completedDate.getTime());
    }

    @SuppressWarnings("deprecation")
    private CompletedTask() {}
}
