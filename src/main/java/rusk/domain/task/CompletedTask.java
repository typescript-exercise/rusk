package rusk.domain.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.Validate;

import rusk.common.util.Now;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CompletedTask extends Task {
    
    private Date completedDate;
    
    @JsonCreator
    CompletedTask(@JsonProperty("id") long id, @JsonProperty("registeredDate") Date registeredDate, @JsonProperty("completedDate") Date completedDate) {
        super(id, registeredDate);
        this.setCompletedDate(completedDate);
    }
    
    private void setCompletedDate(Date completedDate) {
        Validate.notNull(completedDate, "完了日時は必須です");
        this.completedDate = new Date(completedDate.getTime());
    }

    static CompletedTask switchFrom(Task task) {
        CompletedTask completedTask = new CompletedTask(task.getId(), task.getRegisteredDate(), Now.getForCompletedDate());
        completedTask.overwriteBy(task);
        return completedTask;
    }

    @Override
    public InWorkingTask switchToInWorkingTask() {
        InWorkingTask inWorkingTask = InWorkingTask.switchFrom(this);
        return inWorkingTask;
    }
    
    @Override
    public void setPriority(Priority priority) {
        Importance importance = priority.getImportance();
        Date period = priority.getUrgency().getPeriod();
        
        CompletedUrgency completedUrgency = new CompletedUrgency(period);
        Priority completedPrioriry = new Priority(completedUrgency, importance);
        
        super.setPriority(completedPrioriry);
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
        return new ArrayList<>(Arrays.asList(Status.IN_WORKING));
    }

    @Override
    public Date getCompletedDate() {
        return new Date(this.completedDate.getTime());
    }
}
