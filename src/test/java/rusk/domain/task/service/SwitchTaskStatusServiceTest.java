package rusk.domain.task.service;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Verifications;

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

@RunWith(Enclosed.class)
public class SwitchTaskStatusServiceTest {

    public static class 現在作業中のタスクがない場合 {
        @Mocked
        private TaskRepository repository;
        
        private SwitchTaskStatusService service;
        
        private SwitchStatusForm form;
        private Task targetTask;
        
        @Before
        public void setup() {
            service = new SwitchTaskStatusService(repository);
            
            setupForm();
            setupTargetTask();
            setupInWorkingTask();
        }

        private void setupInWorkingTask() {
            new NonStrictExpectations() {{
                repository.inquireTaskInWorkingWithLock(); result = null;
            }};
        }

        private void setupTargetTask() {
            targetTask = TaskBuilder.inWorkingTask(form.id, "2014-07-14 08:00:00", "2014-07-14 09:00:00")
                                    .updateDate(form.lastUpdateDate)
                                    .build();
            
            new NonStrictExpectations() {{
                repository.inquireWithLock(form.id); result = targetTask;
            }};
        }

        private void setupForm() {
            form = new SwitchStatusForm();
            form.id = 10L;
            form.status = Status.STOPPED;
            form.lastUpdateDate = DateUtil.create("2014-07-14 09:00:00");
        }
        
        @Test
        public void 指定したタスクを中断に変更した場合_状態を中断にしたタスクがリポジトリに保存されること() {
            // exercise
            service.switchTaskStatus(form);
            
            // verify
            new Verifications() {{
                Task task;
                Date lastUpdateDate;
                
                repository.saveModification(task = withCapture(), lastUpdateDate = withCapture());
                
                assertThat(task.getId(), is(form.id));
                assertThat(task.getStatus(), is(Status.STOPPED));
                assertThat(lastUpdateDate, is(form.lastUpdateDate));
            }};
        }
        
        @Test(expected=ConcurrentUpdateException.class)
        public void 指定したタスクが同時更新されている場合_例外がスローされること() {
            // setup
            TaskBuilder.with(targetTask)
                        .updateDate(DateUtil.addMilliseconds(form.lastUpdateDate, 1));
            
            // exercise
            service.switchTaskStatus(form);
        }
    }
    
    public static class 現在作業中のタスクが存在する場合 {
        @Mocked
        private TaskRepository repository;
        
        private SwitchTaskStatusService service;
        
        private SwitchStatusForm form;
        private Task targetTask;
        private Task inWorkingTask;
        
        @Before
        public void setup() {
            service = new SwitchTaskStatusService(repository);
            
            setupForm();
            setupInWorkingTask();
            setupTargetTask();
        }
        
        private void setupForm() {
            form = new SwitchStatusForm();
            form.id = 10L;
            form.status = Status.IN_WORKING;
            form.lastUpdateDate = DateUtil.create("2014-07-14 09:00:00");
            form.inWorkingTaskId = 11L;
            form.inWorkingTaskLastUpdateDate = DateUtil.create("2014-07-14 08:00:00");
        }

        private void setupTargetTask() {
            targetTask = TaskBuilder.unstartedTask(form.id, "2014-07-14 08:00:00")
                                     .updateDate(form.lastUpdateDate)
                                     .build();
            
            new NonStrictExpectations() {{
                repository.inquireWithLock(form.id); result = targetTask;
            }};
        }

        private void setupInWorkingTask() {
            inWorkingTask = TaskBuilder.inWorkingTask(form.inWorkingTaskId, "2014-07-14 08:00:00", "2014-07-14 08:00:00")
                                                    .updateDate(form.inWorkingTaskLastUpdateDate)
                                                    .build();
            new NonStrictExpectations() {{
                repository.inquireTaskInWorkingWithLock(); result = inWorkingTask;
            }};
        }

        @Test
        public void 指定したタスクを作業中以外に変更した場合_現在作業中のタスクは変更されないこと() {
            // setup
            form.status = Status.COMPLETE;
            
            // exercise
            service.switchTaskStatus(form);
            
            // verify
            new Verifications() {{
                repository.saveModification(inWorkingTask, form.inWorkingTaskLastUpdateDate); times = 0;
            }};
        }

        @Test
        public void 指定したタスクを作業中に変更した場合_現在作業中のタスクが中断になってリポジトリに保存されること() {
            // exercise
            service.switchTaskStatus(form);
            
            // verify
            new Verifications() {{
                List<Task> tasks = new ArrayList<>();
                List<Date> lastUpdateDates = new ArrayList<>();

                repository.saveModification(withCapture(tasks), withCapture(lastUpdateDates));
                
                assertThat(tasks.get(0).getId(), is(form.inWorkingTaskId));
                assertThat(tasks.get(0).getStatus(), is(Status.STOPPED));
                assertThat(lastUpdateDates.get(0), is(form.inWorkingTaskLastUpdateDate));
            }};
        }

        @Test(expected=ConcurrentUpdateException.class)
        public void フォームに作業中タスクの情報が設定されていない場合_例外がスローされること() {
            // setup
            form.inWorkingTaskId = null;
            form.inWorkingTaskLastUpdateDate = null;
            
            // exercise
            service.switchTaskStatus(form);
        }

        @Test(expected=ConcurrentUpdateException.class)
        public void フォームに設定されていた作業中タスクのIDと_リポジトリが返した作業中タスクが異なる場合_例外がスローされること() {
            // setup
            form.inWorkingTaskId = Long.MAX_VALUE;
            
            // exercise
            service.switchTaskStatus(form);
        }

        @Test(expected=ConcurrentUpdateException.class)
        public void 作業中タスクが同時更新されている場合_例外がスローされること() {
            // setup
            form.inWorkingTaskLastUpdateDate = DateUtil.addMilliseconds(inWorkingTask.getUpdateDate(), -1);
            
            // exercise
            service.switchTaskStatus(form);
        }
    }
    

}
