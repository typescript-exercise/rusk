package rusk.domain.task.form;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import rusk.common.util.Dto;
import rusk.domain.task.Importance;

@Dto
public class RegisterTaskForm {
    
    public String title;
    public Date period;
    public Importance importance;
    public String detail;
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
