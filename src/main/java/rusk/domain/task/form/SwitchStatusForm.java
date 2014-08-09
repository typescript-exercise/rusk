package rusk.domain.task.form;

import java.util.Date;

import rusk.common.util.Dto;
import rusk.domain.task.Status;

@Dto
public class SwitchStatusForm {
    
    public long id;
    public Date lastUpdateDate;
    public Status status;
    
}
