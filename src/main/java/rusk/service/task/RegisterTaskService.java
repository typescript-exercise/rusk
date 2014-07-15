package rusk.service.task;

import javax.inject.Inject;

import rusk.Transactional;
import rusk.domain.task.Task;
import rusk.domain.task.TaskFactory;
import rusk.domain.task.TaskRepository;
import rusk.rest.task.RegisterTaskForm;

@Transactional
public class RegisterTaskService {
    
    private final TaskRepository repository;
    
    @Inject
    public RegisterTaskService(TaskRepository repository) {
        this.repository = repository;
    }

    /**
     * 指定したフォームでタスクを登録する。
     * 
     * @param form 登録するフォーム情報
     * @return 登録されたタスク
     */
    public Task register(RegisterTaskForm form) {
        Task task = TaskFactory.create(form);
        task.register(this.repository);
        return task;
    }
}
