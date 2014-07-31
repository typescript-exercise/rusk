package rusk.domain.task;

import java.util.Date;
import java.util.List;

import rusk.rest.task.RegisterTaskForm;
import rusk.util.Now;

public class TaskFactory {
    
    private Task task;
    
    public static Task create(RegisterTaskForm form) {
        Date now = Now.get();
        
        Task task = new Task(now);
        task.setTitle(form.title);
        task.setDetail(form.detail);
        
        Priority priority = Priority.of(form.period, form.importance);
        task.setPriority(priority);
        
        return task;
    }
    
    public static TaskFactory withBuilder(long id, Date registeredDate, Date completedDate, Status status) {
        return new TaskFactory(id, registeredDate, completedDate, status);
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
    
    public Task build() {
        return this.task;
    }
    
    private TaskFactory(long id, Date registeredDate, Date completedDate, Status status) {
        this.task = new Task(id, registeredDate, completedDate, status);
    }
}
