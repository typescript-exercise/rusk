package rusk.persistence.task;

import java.sql.Timestamp;

import net.sf.persist.annotations.Column;
import net.sf.persist.annotations.NoColumn;
import net.sf.persist.annotations.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import rusk.domain.task.Importance;
import rusk.domain.task.Status;
import rusk.domain.task.Task;

@Table(name="TASK")
public class TaskTable {
    
    public Long id;
    public String title;
    public byte status;
    public String detail;
    public Timestamp registeredDate;
    public Timestamp completedDate;
    public byte importance;
    public Timestamp period;
    
    public TaskTable() {}
    
    public TaskTable(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.detail = task.getDetail();
        this.setImportance(task);
        this.setStatus(task);
        this.setPeriod(task);
        this.setRegisteredDate(task);
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
    public Timestamp getCompletedDate() {
        return completedDate;
    }
    public void setCompletedDate(Timestamp completedDate) {
        this.completedDate = completedDate;
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

    @NoColumn
    public Importance getImportanceAsEnum() {
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
    
    private void setPeriod(Task task) {
        this.period = new Timestamp(task.getPriority().getUrgency().getPeriod().getTime());
    }
    
    private void setRegisteredDate(Task task) {
        this.registeredDate = new Timestamp(task.getRegisteredDate().getTime());
    }
    @NoColumn
    public Status getStatusAsEnum() {
        switch (this.status) {
        case 0:
            return Status.UNSTARTED;
        case 1:
            return Status.IN_WORKING;
        case 2:
            return Status.STOPPED;
        case 3:
            return Status.COMPLETE;
        }
        
        throw new IllegalArgumentException("status = " + this.status);
    }
    
    private void setStatus(Task task) {
        switch (task.getStatus()) {
        case UNSTARTED:
            this.status = 0;
            return;
        case IN_WORKING:
            this.status = 1;
            return;
        case STOPPED:
            this.status = 2;
            return;
        case COMPLETE:
            this.status = 3;
            return;
        }
        
        throw new IllegalArgumentException("status = " + status);
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
