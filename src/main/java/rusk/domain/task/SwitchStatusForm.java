package rusk.domain.task;

public class SwitchStatusForm {
    
    public long id;
    public StatusForm status;
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public StatusForm getStatus() {
        return status;
    }
    public void setStatus(StatusForm status) {
        this.status = status;
    }
}
