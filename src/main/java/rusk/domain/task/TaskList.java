package rusk.domain.task;

import java.util.List;

/**
 * タスク一覧
 */
public class TaskList {
    
    public Task taskInWorking;
    public List<Task> uncompleteTasks;
    public List<Task> completeTasks;
    
    public Task getTaskInWorking() {
        return taskInWorking;
    }
    public void setTaskInWorking(Task taskInWorking) {
        this.taskInWorking = taskInWorking;
    }
    public List<Task> getUncompleteTasks() {
        return uncompleteTasks;
    }
    public void setUncompleteTasks(List<Task> uncompleteTasks) {
        this.uncompleteTasks = uncompleteTasks;
    }
    public List<Task> getCompleteTasks() {
        return completeTasks;
    }
    public void setCompleteTasks(List<Task> completeTasks) {
        this.completeTasks = completeTasks;
    }
}
