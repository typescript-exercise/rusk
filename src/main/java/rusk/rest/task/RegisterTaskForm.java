package rusk.rest.task;

import java.util.Date;

import rusk.domain.task.Importance;

public class RegisterTaskForm {
    
    private String title;
    private Date period;
    private Importance importance;
    private String detail;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Date getPeriod() {
        return period;
    }
    public void setPeriod(Date period) {
        this.period = period;
    }
    public Importance getImportance() {
        return importance;
    }
    public void setImportance(Importance importance) {
        this.importance = importance;
    }
    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }
    
}
