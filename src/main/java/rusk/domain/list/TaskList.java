package rusk.domain.list;

import java.util.Comparator;
import java.util.List;

import rusk.domain.task.Task;
import rusk.domain.task.TaskRepository;
import rusk.util.Now;

/**
 * タスク一覧
 */
public class TaskList {
    
    private Task taskInWorking;
    private List<Task> uncompleteTasks;
    private List<Task> completeTasks;
    
    private static final Comparator<Task> ORDER_BY_PRIORITY_DESC = (t1, t2) -> t2.getPriority().compareTo(t1.getPriority());
    
    /**
     * タスク一覧を取得する。
     * 
     * @param repository 永続化層にアクセスするためのリポジトリ
     * @return タスク一覧
     */
    public static TaskList getTaskList(TaskRepository repository) {
        TaskList taskList = new TaskList();
        
        taskList.taskInWorking = repository.inquireTaskInWorking();
        
        taskList.uncompleteTasks = repository.inquireUncompletedTasks();
        taskList.uncompleteTasks.sort(ORDER_BY_PRIORITY_DESC);
        
        taskList.completeTasks = repository.inquireCompleteTasks(Now.getForInquireCompletedTaskInToday());
        taskList.completeTasks.sort(ORDER_BY_PRIORITY_DESC);
        
        return taskList;
    }
    
    public Task getTaskInWorking() {
        return this.taskInWorking;
    }

    public List<Task> getUncompleteTasks() {
        return this.uncompleteTasks;
    }

    public List<Task> getCompleteTasks() {
        return this.completeTasks;
    }
}
