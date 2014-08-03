package rusk.service.task;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rusk.Transactional;
import rusk.domain.task.SwitchStatusForm;
import rusk.domain.task.SwitchTaskStatusService;
import rusk.domain.task.Task;
import rusk.domain.task.TaskFactory;
import rusk.domain.task.TaskNotFoundException;
import rusk.domain.task.TaskRepository;

@Transactional
public class TaskService {
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    
    private final TaskRepository repository;
    private final SwitchTaskStatusService switchTaskStatusService;
    
    @Inject
    public TaskService(TaskRepository repository, SwitchTaskStatusService switchTaskStatusService) {
        this.repository = repository;
        this.switchTaskStatusService = switchTaskStatusService;
    }
    
    /**
     * 指定した ID のタスクを取得する。
     * @param id タスク ID
     * @return 取得したタスク
     */
    public Task inquire(long id) {
        return this.repository.inquireById(id);
    }

    /**
     * 指定した ID のタスクを削除する。
     * 
     * @param deleteTargetTaskId 削除するタスクの ID
     * @throws TaskNotFoundException 指定した ID のタスクが存在しない場合
     */
    public void remove(long deleteTargetTaskId) throws TaskNotFoundException {
        this.repository.remove(deleteTargetTaskId);
    }
    
    /**
     * 指定したフォームでタスクを登録する。
     * 
     * @param form 登録するフォーム情報
     * @return 登録されたタスク
     */
    public Task register(RegisterTaskForm form) {
        Task task = TaskFactory.create(form);
        this.repository.register(task);
        
        logger.info("created new task. id = " + task.getId());
        
        return task;
    }
    
    /**
     * タスクの状態を変更します。
     * 
     * @param form タスク状態切り替えフォーム
     */
    public void switchTaskStatus(SwitchStatusForm form) {
        this.switchTaskStatusService.switchTaskStatus(form);
    }
}
