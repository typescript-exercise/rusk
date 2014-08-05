package rusk.domain.task.form;

import rusk.common.util.Dto;
import rusk.domain.task.Status;

@Dto
public class SwitchStatusForm {
    
    public long id;
    public Status status;
    
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
}
