package rusk.domain.task.form;

import java.util.Date;

import rusk.common.util.Dto;
import rusk.domain.task.Status;

@Dto
public class SwitchStatusForm {
    
    public long id;
    public Date lastUpdateDate;
    public Status status;
    
    public Long inWorkingTaskId;
    public Date inWorkingTaskLastUpdateDate;
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
    public Long getInWorkingTaskId() {
        return inWorkingTaskId;
    }
    public void setInWorkingTaskId(Long inWorkingTaskId) {
        this.inWorkingTaskId = inWorkingTaskId;
    }
    public Date getInWorkingTaskLastUpdateDate() {
        return inWorkingTaskLastUpdateDate;
    }
    public void setInWorkingTaskLastUpdateDate(Date inWorkingTaskLastUpdateDate) {
        this.inWorkingTaskLastUpdateDate = inWorkingTaskLastUpdateDate;
    }
    public boolean hasInWorkingParameter() {
        return this.inWorkingTaskId != null || this.inWorkingTaskLastUpdateDate != null;
    }
}
