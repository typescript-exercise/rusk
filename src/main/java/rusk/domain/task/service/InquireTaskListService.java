package rusk.domain.task.service;

import java.util.Comparator;

import javax.inject.Inject;

import rusk.common.util.Now;
import rusk.domain.task.Task;
import rusk.domain.task.TaskList;
import rusk.domain.task.TaskRepository;

public class InquireTaskListService {
    
    private static final Comparator<Task> ORDER_BY_PRIORITY_DESC = (t1, t2) -> t2.getPriority().compareTo(t1.getPriority());
    
    private final TaskRepository repository;

    @Inject
    public InquireTaskListService(TaskRepository repository) {
        this.repository = repository;
    }
    
    public TaskList inquireTaskList() {
        TaskList taskList = new TaskList();
        
        taskList.taskInWorking = this.repository.inquireTaskInWorking();
        
        taskList.uncompleteTasks = this.repository.inquireUncompletedTasks();
        taskList.uncompleteTasks.sort(ORDER_BY_PRIORITY_DESC);
        
        taskList.completeTasks = this.repository.inquireCompleteTasks(Now.getForInquireCompletedTaskInToday());
        taskList.completeTasks.sort(ORDER_BY_PRIORITY_DESC);
        
        return taskList;
    }
    
}
