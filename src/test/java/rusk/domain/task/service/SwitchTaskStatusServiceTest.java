package rusk.domain.task.service;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import mockit.Mocked;
import mockit.Verifications;

import org.junit.Before;
import org.junit.Test;

import rusk.domain.task.CompletedTask;
import rusk.domain.task.InWorkingTask;
import rusk.domain.task.Status;
import rusk.domain.task.StoppedTask;
import rusk.domain.task.Task;
import rusk.domain.task.TaskBuilder;
import rusk.domain.task.TaskRepository;

public class SwitchTaskStatusServiceTest {
    
    @Mocked
    private TaskRepository repository;
    
    private SwitchTaskStatusService service;
    
    @Before
    public void setup() {
        service = new SwitchTaskStatusService(repository);
    }
    
    @Test
    public void 変更後の状態のタスクがリポジトリに保存されること() {
        // setup
        Task targetTask = TaskBuilder.unstartedTask(10L, "2014-01-01 11:00:00");
        InWorkingTask inWorkingTask = null;
        Status toStatus = Status.COMPLETE;
        
        // exercise
        service.switchTaskStatus(targetTask, inWorkingTask, toStatus);
        
        // verify
        new Verifications() {{
            Task task;
            
            repository.saveModification(task = withCapture());
            
            assertThat(task, is(instanceOf(CompletedTask.class)));
            assertThat(task.getId(), is(targetTask.getId()));
        }};
    }
    
    @Test
    public void 変更後の状態が作業中の場合で_現在作業中のタスクが存在する場合_現在作業中のタスクが中断になってリポジトリに保存されること() {
        // setup
        Task targetTask = TaskBuilder.unstartedTask(10L, "2014-01-01 11:00:00");
        InWorkingTask inWorkingTask = (InWorkingTask)TaskBuilder.inWorkingTask(11L, "2014-01-01 11:00:00", "2014-01-01 11:00:00").build();
        Status toStatus = Status.IN_WORKING;
        
        // exercise
        service.switchTaskStatus(targetTask, inWorkingTask, toStatus);
        
        // verify
        new Verifications() {{
            List<Task> savedTasks = new ArrayList<>();
            
            repository.saveModification(withCapture(savedTasks));
            
            assertThat(savedTasks.get(0), is(instanceOf(StoppedTask.class)));
            assertThat(savedTasks.get(0).getId(), is(inWorkingTask.getId()));
        }};
    }
    
    @Test
    public void 変更後の状態が作業中でない場合で_現在作業中のタスクが存在する場合_現在作業中のタスクは変更されないこと() {
        // setup
        Task targetTask = TaskBuilder.unstartedTask(10L, "2014-01-01 11:00:00");
        InWorkingTask inWorkingTask = (InWorkingTask)TaskBuilder.inWorkingTask(11L, "2014-01-01 11:00:00", "2014-01-01 11:00:00").build();
        Status toStatus = Status.COMPLETE;
        
        // exercise
        service.switchTaskStatus(targetTask, inWorkingTask, toStatus);
        
        // verify
        new Verifications() {{
            Task task;
            
            repository.saveModification(task = withCapture()); times = 1;
            
            assertThat(task, is(instanceOf(CompletedTask.class)));
            assertThat(task.getId(), is(targetTask.getId()));
        }};
    }
}
