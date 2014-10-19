package rusk.domain.task.form;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ModifyWorkTimeForm {

    public long taskId;
    public Long workTimeId;
    public Date startTime;
    public Date endTime;
    public Date updateDate;

}
