package rusk.domain.task;

import java.util.List;

import rusk.common.util.Dto;

/**
 * タスク一覧
 */
@Dto
public class TaskList {
    
    public InWorkingTask taskInWorking;
    public List<Task> uncompleteTasks;
    public List<Task> completeTasks;
    
}
