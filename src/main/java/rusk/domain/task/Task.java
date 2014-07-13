package rusk.domain.task;

import java.util.Date;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Task {
    
    private final Long id;
    private String title;
    private String detail;
    private final Date registeredDate;
    private Date completedDate;
    private Priority priority;
    private Status status;
    
    public Task() {
        this.id = null;
        this.registeredDate = null;
    }
    
    public Task(long id, Date registeredDate) {
        Validate.isTrue(0 < id, "ID は 1 以上の値のみ受け付けます。");
        Validate.notNull(registeredDate, "登録日は必須です。");
        
        this.id = id;
        this.registeredDate = registeredDate;
    }
    
    public void setTitle(String title) {
        Validate.notEmpty(title, "タイトルは必須です。");
        this.title = title;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }
    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }
    public void setPriority(Priority priority) {
        Validate.notNull(priority, "優先度は必須です。");
        this.priority = priority;
    }
    public void setStatus(Status status) {
        Validate.notNull(status, "状態は必須です。");
        this.status = status;
    }
    public Long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDetail() {
        return detail;
    }
    public Date getRegisteredDate() {
        return new Date(registeredDate.getTime());
    }
    public Date getCompletedDate() {
        return this.completedDate == null ? null : new Date(completedDate.getTime());
    }
    public Priority getPriority() {
        return priority;
    }
    public Status getStatus() {
        return status;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
