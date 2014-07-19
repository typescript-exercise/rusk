package rusk.service.task;

import javax.inject.Inject;

import rusk.domain.task.Task;
import rusk.domain.task.TaskRepository;

public class InquireTaskService {
    
    private final TaskRepository repository;

    @Inject
    public InquireTaskService(TaskRepository repository) {
        this.repository = repository;
    }
    
    /**
     * 指定した ID のタスクを取得する。
     * @param id タスク ID
     * @return 取得したタスク
     */
    public Task inquire(long id) {
        return this.repository.inquire(id);
    }
}
