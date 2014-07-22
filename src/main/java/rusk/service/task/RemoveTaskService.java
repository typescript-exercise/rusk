package rusk.service.task;

import javax.inject.Inject;

import rusk.Transactional;
import rusk.domain.task.Task;
import rusk.domain.task.TaskNotFoundException;
import rusk.domain.task.TaskRepository;

@Transactional
public class RemoveTaskService {
    
    private final TaskRepository repository;
    
    @Inject
    public RemoveTaskService(TaskRepository repository) {
        this.repository = repository;
    }

    /**
     * 指定した ID のタスクを削除する。
     * 
     * @param deleteTargetTaskId 削除するタスクの ID
     * @throws TaskNotFoundException 指定した ID のタスクが存在しない場合
     */
    public void remove(long deleteTargetTaskId) throws TaskNotFoundException {
        Task.remove(this.repository, deleteTargetTaskId);
    }
}
