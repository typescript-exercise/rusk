package rusk.domain.task.form;

import java.util.Date;

import rusk.common.util.Dto;
import rusk.domain.task.Importance;
import rusk.domain.task.Status;

@Dto
public class ModifyTaskForm {

    public long id;
    public String title;
    public Status status;
    public Date period;
    public Importance importance;
    public String detail;
    public Date lastUpdateDate;
    @Override
    public String toString() {
        return "ModifyTaskForm [id=" + id + ", title=" + title + ", status=" + status + ", period=" + period + ", importance=" + importance + ", detail=" + detail + ", lastUpdateDate=" + lastUpdateDate + "]";
    }
    
}
