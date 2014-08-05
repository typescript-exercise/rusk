package rusk.application.facade.task;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rusk.application.interceptor.Transactional;
import rusk.domain.task.Task;
import rusk.domain.task.TaskFactory;
import rusk.domain.task.TaskList;
import rusk.domain.task.TaskRepository;
import rusk.domain.task.exception.TaskNotFoundException;
import rusk.domain.task.form.RegisterTaskForm;
import rusk.domain.task.form.SwitchStatusForm;
import rusk.domain.task.service.InquireTaskListService;
import rusk.domain.task.service.SwitchTaskStatusService;

@Transactional
public class TaskFacade {
    private static final Logger logger = LoggerFactory.getLogger(TaskFacade.class);
    
    private final TaskRepository repository;
    private final SwitchTaskStatusService switchTaskStatusService;
    private final InquireTaskListService inquireTaskListService;
    
    @Inject
    public TaskFacade(TaskRepository repository, SwitchTaskStatusService switchTaskStatusService, InquireTaskListService inquireTaskListService) {
        this.repository = repository;
        this.switchTaskStatusService = switchTaskStatusService;
        this.inquireTaskListService = inquireTaskListService;
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
     * タスク一覧を取得する。
     * 
     * @return タスク一覧
     */
    public TaskList inquireTaskList() {
        return this.inquireTaskListService.inquireTaskList();
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
