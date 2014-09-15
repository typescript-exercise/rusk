package rusk.application.facade.task;

import java.util.Date;
import java.util.function.BiConsumer;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rusk.application.exception.ConcurrentUpdateException;
import rusk.application.interceptor.Transactional;
import rusk.domain.task.InWorkingTask;
import rusk.domain.task.Task;
import rusk.domain.task.TaskFactory;
import rusk.domain.task.TaskList;
import rusk.domain.task.TaskRepository;
import rusk.domain.task.WorkTime;
import rusk.domain.task.exception.TaskNotFoundException;
import rusk.domain.task.form.ModifyTaskForm;
import rusk.domain.task.form.ModifyWorkTimeForm;
import rusk.domain.task.form.RegisterTaskForm;
import rusk.domain.task.form.SwitchStatusForm;
import rusk.domain.task.service.InquireTaskListService;
import rusk.domain.task.service.ModifyTaskService;
import rusk.domain.task.service.SwitchTaskStatusService;

@Transactional
public class TaskFacade {
    private static final Logger logger = LoggerFactory.getLogger(TaskFacade.class);
    
    private final TaskRepository repository;
    private final SwitchTaskStatusService switchTaskStatusService;
    private final InquireTaskListService inquireTaskListService;
    private final ModifyTaskService modifyTaskService;
    
    @Inject
    public TaskFacade(TaskRepository repository, SwitchTaskStatusService switchTaskStatusService, InquireTaskListService inquireTaskListService, ModifyTaskService modifyTaskService) {
        this.repository = repository;
        this.switchTaskStatusService = switchTaskStatusService;
        this.inquireTaskListService = inquireTaskListService;
        this.modifyTaskService = modifyTaskService;
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
        this.modifyTaskWithLock(form.id, form.lastUpdateDate, (storedTask, inWorkingTask) -> {
            this.switchTaskStatusService.switchTaskStatus(storedTask, inWorkingTask, form.status);
        });
    }

    /**
     * タスクを更新します。
     * 
     * @param form 更新情報
     */
    public void modify(ModifyTaskForm form) {
        this.modifyTaskWithLock(form.id, form.lastUpdateDate, (storedTask, inWorkingTask) -> {
            this.modifyTaskService.modify(storedTask, form, inWorkingTask);
        });
    }
    
    private void modifyTaskWithLock(long id, Date lastUpdateDate, BiConsumer<Task, InWorkingTask> consumer) {
        InWorkingTask inWorkingTask = this.repository.inquireTaskInWorkingWithLock();
        Task storedTask = this.repository.inquireWithLock(id);

        this.verifyConcurrentUpdate(storedTask, lastUpdateDate);
        consumer.accept(storedTask, inWorkingTask);
    }

    private void verifyConcurrentUpdate(Task storedTask, Date lastUpdateDate) {
        if (lastUpdateDate.getTime() < storedTask.getUpdateDate().getTime()) {
            throw new ConcurrentUpdateException();
        }
    }
    
    /**
     * 作業時間を新規に追加する。
     * 
     * @param form 登録情報
     */
    public void registerWorkTime(ModifyWorkTimeForm form) {
        // 全作業時間との整合性チェックを行うので、 WorkTime.class で同期を取る。
        synchronized (WorkTime.class) {
            Task task = this.repository.inquireWithLock(form.taskId);
            this.modifyTaskService.registerWorkTime(task, form);
        }
    }
}
