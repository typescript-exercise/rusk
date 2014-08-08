package rusk.application.facade.task;

import java.util.Date;

import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import rusk.common.util.DateUtil;
import rusk.domain.ConcurrentUpdateException;
import rusk.domain.task.Status;
import rusk.domain.task.Task;
import rusk.domain.task.TaskBuilder;
import rusk.domain.task.TaskRepository;
import rusk.domain.task.form.SwitchStatusForm;
import rusk.domain.task.service.InquireTaskListService;
import rusk.domain.task.service.SwitchTaskStatusService;

@RunWith(Enclosed.class)
public class TaskFacadeTest {
    
    private static final Date DATETIME_0 = DateUtil.create("2014-07-05 10:00:00");
    private static final Date DATETIME_1 = DateUtil.create("2014-08-01 11:01:01");
    private static final Date DATETIME_2 = DateUtil.create("2014-08-01 13:01:01");
    
    public static class 現在作業中のタスクが存在しない場合 {
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
            form.lastUpdateDate = DATETIME_1;
            
            Task storedTask = TaskBuilder.task(form.id);
            TaskBuilder.with(storedTask).updateDate(DateUtil.addMilliseconds(form.lastUpdateDate, 1));
            
            new NonStrictExpectations() {{
                repository.inquireWithLock(form.id); result = storedTask;
            }};
            
            // exercise
            facade.switchTaskStatus(form);
        }
    }
    
    public static class 現在作業中のタスクが存在せず_更新対象のタスクを作業中に更新した場合 {

        @Mocked
        private TaskRepository repository;
        @Mocked
        private SwitchTaskStatusService switchTaskStatusService;
        @Mocked
        private InquireTaskListService inquireTaskListService;
        
        private TaskFacade facade;
        
        private SwitchStatusForm form;
        
        private Task inWorkingTask;
        
        @Before
        public void setup() {
            facade = new TaskFacade(repository, switchTaskStatusService, inquireTaskListService);
            form = new SwitchStatusForm();

            form.inWorkingTaskId = 11L;
            form.inWorkingTaskLastUpdateDate = DATETIME_2;
            form.status = Status.IN_WORKING;
            
            inWorkingTask = TaskBuilder.inWorkingTask(form.inWorkingTaskId, DATETIME_0, DATETIME_2)
                                        .updateDate(DATETIME_2)
                                        .build();

            new NonStrictExpectations() {{
                repository.inquireTaskInWorkingWithLock(); result = inWorkingTask;
            }};
        }
        
        @Test(expected=ConcurrentUpdateException.class)
        public void 現在作業中のタスクが同時更新されている場合_例外がスローされること() {
            // setup
            TaskBuilder.with(inWorkingTask).updateDate(DateUtil.addMilliseconds(form.inWorkingTaskLastUpdateDate, 1));
            
            // exercise
            facade.switchTaskStatus(form);
        }
        
        @Test(expected=ConcurrentUpdateException.class)
        public void 現在作業中のタスクのIDとパラメータの作業中タスクのIDが異なる場合_例外がスローされること() {
            // setup
            form.inWorkingTaskId = 12L;
            
            // exercise
            facade.switchTaskStatus(form);
        }
        
        
    }

    
    public static class 現在作業中のタスクが存在し_更新対象のタスクを作業中以外に更新した場合 {

        @Mocked
        private TaskRepository repository;
        @Mocked
        private SwitchTaskStatusService switchTaskStatusService;
        @Mocked
        private InquireTaskListService inquireTaskListService;
        
        private TaskFacade facade;
        
        private SwitchStatusForm form;
        
        private Task inWorkingTask;
        
        @Before
        public void setup() {
            facade = new TaskFacade(repository, switchTaskStatusService, inquireTaskListService);
            form = new SwitchStatusForm();
            form.status = Status.COMPLETE;
            
            setupInWorkingTask();
            setupTargetTask();
        }

        private void setupTargetTask() {
            form.id = 10L;
            form.lastUpdateDate = DATETIME_1;

            Task storedTask = TaskBuilder.task(form.id);
            TaskBuilder.with(storedTask).updateDate(DATETIME_1);
            
            new NonStrictExpectations() {{
                repository.inquireWithLock(form.id); result = storedTask;
            }};
        }

        private void setupInWorkingTask() {
            form.inWorkingTaskId = 11L;
            form.inWorkingTaskLastUpdateDate = DATETIME_2;
            
            inWorkingTask = TaskBuilder.inWorkingTask(form.inWorkingTaskId, DATETIME_0, DATETIME_2)
                                        .updateDate(DATETIME_2)
                                        .build();

            new NonStrictExpectations() {{
                repository.inquireTaskInWorkingWithLock(); result = inWorkingTask;
            }};
        }
        
        @Test
        public void 現在作業中のタスクが同時更新されていても_例外がスローされないこと() {
            // setup
            TaskBuilder.with(inWorkingTask).updateDate(DateUtil.addMilliseconds(form.inWorkingTaskLastUpdateDate, 1));
            
            // exercise
            facade.switchTaskStatus(form);
        }
        
        @Test
        public void 現在作業中のタスクのIDとパラメータの作業中タスクのIDが異なっていても_例外がスローされないこと() {
            // setup
            form.inWorkingTaskId = 12L;
            
            // exercise
            facade.switchTaskStatus(form);
        }
        
        
    }
    
    
}
