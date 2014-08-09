package rusk.domain.task;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import rusk.common.util.Now;
import rusk.domain.task.form.RegisterTaskForm;

public class TaskFactory {
    
    private Task task;
    
    public static Task create(RegisterTaskForm form) {
        Date now = Now.getForRegisteredDate();
        UnstartedTask task = new UnstartedTask(now);
        task.setTitle(form.title);
        task.setDetail(form.detail);
        task.setUpdateDate(now);
        
        Priority priority = Priority.of(form.period, form.importance);
        task.setPriority(priority);
        
        return task;
    }
    
    public static TaskFactory unstartedTaskWithBuilder(long id, Date registeredDate) {
        TaskFactory factory = new TaskFactory();
        factory.task = new UnstartedTask(id, registeredDate);
        return factory;
    }
    
    public static TaskFactory inWorkingTaskWithBuilder(long id, Date registeredDate) {
        TaskFactory factory = new TaskFactory();
        factory.task = new InWorkingTask(id, registeredDate);
        return factory;
    }
    
    public static TaskFactory stoppedTaskWithBuilder(long id, Date registeredDate) {
        TaskFactory factory = new TaskFactory();
        factory.task = new StoppedTask(id, registeredDate);
        return factory;
    }
    
    public static TaskFactory completedTaskWithBuilder(long id, Date registeredDate, Date completedDate) {
        TaskFactory factory = new TaskFactory();
        factory.task = new CompletedTask(id, registeredDate, completedDate);
        return factory;
    }
    
    public TaskFactory title(String title) {
        this.task.setTitle(title);
        return this;
    }
    
    public TaskFactory detail(String detail) {
        this.task.setDetail(detail);
        return this;
    }
    
    public TaskFactory priority(Date period, Importance importance) {
        this.task.setPriority(Priority.of(period, importance));
        return this;
    }
    
    public TaskFactory workTimes(List<WorkTime> workTimes) {
        this.task.setWorkTimes(workTimes);
        return this;
    }

    public TaskFactory updateDate(Timestamp updateDate) {
        this.task.setUpdateDate(updateDate);
        return this;
    }
    
    public Task build() {
        return this.task;
    }

    private TaskFactory() {}
}
