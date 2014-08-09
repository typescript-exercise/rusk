package rusk.application.facade.task;

import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.junit.Before;
import org.junit.Test;

import rusk.application.exception.ConcurrentUpdateException;
import rusk.common.util.DateUtil;
import rusk.domain.task.Task;
import rusk.domain.task.TaskBuilder;
import rusk.domain.task.TaskRepository;
import rusk.domain.task.form.SwitchStatusForm;
import rusk.domain.task.service.InquireTaskListService;
import rusk.domain.task.service.SwitchTaskStatusService;

public class TaskFacadeTest {
    
    @Mocked
    private TaskRepository repository;
    @Mocked
    private SwitchTaskStatusService switchTaskStatusService;
    @Mocked
    private InquireTaskListService inquireTaskListService;
    
    private TaskFacade facade;
    
    private SwitchStatusForm form;
    
    @Before
    public void setup() {
        facade = new TaskFacade(repository, switchTaskStatusService, inquireTaskListService);
        form = new SwitchStatusForm();
    }
    
    @Test(expected=ConcurrentUpdateException.class)
    public void 更新対象のタスクが同時更新されている場合_例外がスローされること() {
        // setup
        form.id = 10L;
        form.lastUpdateDate = DateUtil.create("2014-08-01 11:01:01");
        
        this.setupTargetTask();
        
        // exercise
        facade.switchTaskStatus(form);
    }

    private void setupTargetTask() {
        Task storedTask = TaskBuilder.task(form.id);
        TaskBuilder.with(storedTask).updateDate(DateUtil.addMilliseconds(form.lastUpdateDate, 1));
        
        new NonStrictExpectations() {{
            repository.inquireWithLock(form.id); result = storedTask;
        }};
    }
}
