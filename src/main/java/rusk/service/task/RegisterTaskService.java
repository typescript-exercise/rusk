package rusk.service.task;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rusk.Transactional;
import rusk.domain.task.Task;
import rusk.domain.task.TaskFactory;
import rusk.domain.task.TaskRepository;
import rusk.rest.task.RegisterTaskForm;

@Transactional
public class RegisterTaskService {
    private static final Logger logger = LoggerFactory.getLogger(RegisterTaskService.class);
    
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
        
        logger.info("created new task. id = " + task.getId());
        
        return task;
    }
}
