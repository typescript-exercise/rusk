package rusk.persistence.task;

import java.sql.Timestamp;

import net.sf.persist.annotations.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Table(name="TASK")
public class TaskTable {
    
    private Long id;
    private String title;
    private byte status;
    private String detail;
    private Timestamp registeredDate;
    private Timestamp completedDate;
    private byte importance;
    private Timestamp period;
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
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}