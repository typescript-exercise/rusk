package rusk.persistence.task;

import java.sql.Timestamp;

import net.sf.persist.annotations.Table;

@Table(name="WORK_TIME")
public class WorkTimeTable {
    
    private long taskId;
    private Timestamp startTime;
    private Timestamp endTime;
    public long getTaskId() {
        return taskId;
    }
    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }
    public Timestamp getStartTime() {
        return startTime;
    }
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }
    public Timestamp getEndTime() {
        return endTime;
    }
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
    @Override
    public String toString() {
        return "WorkTimeTable [taskId=" + taskId + ", startTime=" + startTime + ", endTime=" + endTime + "]";
    }
}
