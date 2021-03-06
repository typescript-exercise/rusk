package rusk.persistence.task;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import net.sf.persist.annotations.Column;
import net.sf.persist.annotations.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import rusk.domain.task.CompletedTask;
import rusk.domain.task.Importance;
import rusk.domain.task.InWorkingTask;
import rusk.domain.task.StoppedTask;
import rusk.domain.task.Task;
import rusk.domain.task.TaskFactory;
import rusk.domain.task.UnstartedTask;
import rusk.domain.task.WorkTime;

@Table(name="TASK")
public class TaskTable {
    
    private static final int UNSTARTED = 0;
    private static final int IN_WORKING = 1;
    private static final int STOPPED = 2;
    private static final int COMPLETE = 3;
    
    public Long id;
    public String title;
    public byte status;
    public String detail;
    public Timestamp registeredDate;
    public Timestamp completedDate;
    public byte importance;
    public Timestamp period;
    public Timestamp updateDate;
    
    private List<WorkTime> workTimes;
    
    public TaskTable() {}
    
    public TaskTable(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.detail = task.getDetail();
        this.setImportance(task);
        this.setStatus(task);
        this.setPeriod(task);
        this.setRegisteredDate(task);
        this.setCompletedDate(task);
        this.setUpdateDate(task);
    }

    private void setImportance(Task task) {
        switch (task.getPriority().getImportance()) {
        case C:
            this.importance = 0;
            return;
        case B:
            this.importance = 1;
            return;
        case A:
            this.importance = 2;
            return;
        case S:
            this.importance = 3;
            return;
        }
        
        throw new IllegalArgumentException("importance = " + importance);
    }
    
    private void setStatus(Task task) {
        if (task instanceof UnstartedTask) {
            this.status = UNSTARTED;
        } else if (task instanceof InWorkingTask) {
            this.status = IN_WORKING;
        } else if (task instanceof StoppedTask) {
            this.status = STOPPED;
        } else if (task instanceof CompletedTask) {
            this.status = COMPLETE;
        } else {
            throw new IllegalArgumentException("task.class = " + task.getClass());
        }
    }
    
    private void setPeriod(Task task) {
        this.period = new Timestamp(task.getPriority().getUrgency().getPeriod().getTime());
    }
    
    private void setRegisteredDate(Task task) {
        this.registeredDate = new Timestamp(task.getRegisteredDate().getTime());
    }
    
    private void setCompletedDate(Task task) {
        if (task instanceof CompletedTask) {
            this.completedDate = new Timestamp(task.getCompletedDate().getTime());
        }
    }
    
    private void setUpdateDate(Task task) {
        this.updateDate = new Timestamp(task.getUpdateDate().getTime());
    }
    
    public Task convertToTask(List<WorkTime> workTimes) {
        this.copyAndSetWorkTimes(workTimes);
        return this.buildTask();
    }
    
    private void copyAndSetWorkTimes(List<WorkTime> workTimes) {
        this.workTimes = new ArrayList<>(workTimes);
    }

    private Task buildTask() {
        return this.createFactory().title(this.title)
                                    .detail(this.detail)
                                    .priority(this.period, this.getImportanceAsEnum())
                                    .workTimes(this.workTimes)
                                    .updateDate(this.updateDate)
                                    .build();
    }

    private TaskFactory createFactory() {
        switch (this.status) {
        case UNSTARTED:
            return TaskFactory.unstartedTaskWithBuilder(this.id, this.registeredDate);
            
        case IN_WORKING:
            return TaskFactory.inWorkingTaskWithBuilder(this.id, this.registeredDate);
            
        case STOPPED:
            return TaskFactory.stoppedTaskWithBuilder(this.id, this.registeredDate);
            
        case COMPLETE:
            return TaskFactory.completedTaskWithBuilder(this.id, this.registeredDate, this.completedDate);
            
        }
        
        throw new IllegalArgumentException("status = " + this.status);
    }

    private Importance getImportanceAsEnum() {
        switch (this.importance) {
        case 0:
            return Importance.C;
        case 1:
            return Importance.B;
        case 2:
            return Importance.A;
        case 3:
            return Importance.S;
        }
        
        throw new IllegalArgumentException("importance = " + this.importance);
    }
    
    @Column(name="ID", autoGenerated=true)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public byte getStatus() {
        return status;
    }
    public void setStatus(byte status) {
        this.status = status;
    }
    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }
    public Timestamp getRegisteredDate() {
        return registeredDate;
    }
    public void setRegisteredDate(Timestamp registeredDate) {
        this.registeredDate = registeredDate;
    }
    public void setCompletedDate(Timestamp completedDate) {
        this.completedDate = completedDate;
    }
    public Timestamp getCompletedDate() {
        return completedDate;
    }
    public byte getImportance() {
        return importance;
    }
    public void setImportance(byte importance) {
        this.importance = importance;
    }
    public Timestamp getPeriod() {
        return period;
    }
    public void setPeriod(Timestamp period) {
        this.period = period;
    }
    
    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
