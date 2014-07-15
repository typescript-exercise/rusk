package rusk.domain.task;

import java.util.Date;

import rusk.rest.task.RegisterTaskForm;
import rusk.util.Today;

public class TaskFactory {
    
    public static Task create(RegisterTaskForm form) {
        Date now = Today.get();
        
        Task task = new Task(now);
        task.setTitle(form.getTitle());
        task.setDetail(form.getDetail());
        task.setStatus(Status.UNSTARTED);
        
        Priority priority = new Priority(new Urgency(now, form.getPeriod()), form.getImportance());
        task.setPriority(priority);
        
        return task;
    }
    
    private TaskFactory() {}
}
