package rusk.application.facade.task;

import java.util.Date;

import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.junit.Before;
import org.junit.Test;

import rusk.application.exception.ConcurrentUpdateException;
import rusk.common.util.DateUtil;
import rusk.domain.task.Task;
import rusk.domain.task.TaskBuilder;
import rusk.domain.task.TaskRepository;
import rusk.domain.task.form.ModifyTaskForm;
import rusk.domain.task.form.SwitchStatusForm;
import rusk.domain.task.service.InquireTaskListService;
import rusk.domain.task.service.ModifyTaskService;
import rusk.domain.task.service.SwitchTaskStatusService;

public class TaskFacadeTest {
    
    @Mocked
    private TaskRepository repository;
    @Mocked
    private SwitchTaskStatusService switchTaskStatusService;
    @Mocked
    private InquireTaskListService inquireTaskListService;
    @Mocked
    private ModifyTaskService modifyTaskService;
    
    private TaskFacade facade;
    
    private SwitchStatusForm switchStatusForm;
    private ModifyTaskForm modifyTaskForm;
    
    @Before
    public void setup() {
        facade = new TaskFacade(repository, switchTaskStatusService, inquireTaskListService, modifyTaskService);
        switchStatusForm = new SwitchStatusForm();
        modifyTaskForm = new ModifyTaskForm();
    }
    
    @Test(expected=ConcurrentUpdateException.class)
    public void 状態の更新対象タスクが同時更新されている場合_例外がスローされること() {
        // setup
        switchStatusForm.id = 10L;
        switchStatusForm.lastUpdateDate = DateUtil.create("2014-08-01 11:01:01");
        
        this.setupTargetTask(switchStatusForm.id, switchStatusForm.lastUpdateDate);
        
        // exercise
        facade.switchTaskStatus(switchStatusForm);
    }

    private void setupTargetTask(long id, Date lastUpdateDate) {
        Task storedTask = TaskBuilder.task(id);
        TaskBuilder.with(storedTask).updateDate(DateUtil.addMilliseconds(lastUpdateDate, 1));
        
        new NonStrictExpectations() {{
            repository.inquireWithLock(id); result = storedTask;
        }};
    }
    
    @Test(expected=ConcurrentUpdateException.class)
    public void 更新対象のタスクが同時更新されている場合_例外がスローされること() {
        // setup
        modifyTaskForm.id = 10L;
        modifyTaskForm.lastUpdateDate = DateUtil.create("2014-08-01 11:01:01");
        
        this.setupTargetTask(modifyTaskForm.id, modifyTaskForm.lastUpdateDate);
        
        // exercise
        facade.modify(modifyTaskForm);
    }
}
