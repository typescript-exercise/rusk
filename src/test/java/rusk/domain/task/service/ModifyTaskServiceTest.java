package rusk.domain.task.service;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import mockit.Mocked;
import mockit.Verifications;

import org.junit.Before;
import org.junit.Test;

import rusk.common.util.DateUtil;
import rusk.domain.task.Importance;
import rusk.domain.task.InWorkingTask;
import rusk.domain.task.Status;
import rusk.domain.task.Task;
import rusk.domain.task.TaskBuilder;
import rusk.domain.task.TaskRepository;
import rusk.domain.task.form.ModifyTaskForm;

public class ModifyTaskServiceTest {
    
    @Mocked
    private TaskRepository repository;
    
    private ModifyTaskService service;
    private SwitchTaskStatusService switchTaskStatusService;
    
    @Before
    public void setup() {
        switchTaskStatusService = new SwitchTaskStatusService(repository);
        service = new ModifyTaskService(switchTaskStatusService);
    }
    
    @Test
    public void フォームで指定した値に更新されたタスクがリポジトリに保存されること() {
        // setup
        Task task = TaskBuilder.inWorkingTask(10L, "2014-08-01 15:10:10", "2014-08-01 15:30:00")
                                .updateDate("2014-08-10 10:00:00")
                                .build();
        
        ModifyTaskForm form = new ModifyTaskForm();
        form.title = "更新後タイトル";
        form.status = Status.COMPLETE;
        form.period = DateUtil.create("2014-08-10 10:15:10");
        form.importance = Importance.B;
        form.detail = "更新後詳細";
        
        // exercise
        service.modify(task, form, null);
        
        // verify
        new Verifications() {{
            Task task;
            
            repository.saveModification(task = withCapture());
            
            assertThat(task.getId(), is(10L));
            assertThat(task.getTitle(), is(form.title));
            assertThat(task.getStatus(), is(form.status));
            assertThat(task.getPriority().getUrgency().getPeriod(), is(form.period));
            assertThat(task.getPriority().getImportance(), is(form.importance));
            assertThat(task.getDetail(), is(form.detail));
        }};
    }
    
    @Test
    public void 作業中タスクがnullでなく_かつ更新後の状態が作業中の場合_作業中タスクが中断に更新されること() {
        // setup
        Task task = TaskBuilder.unstartedTask(10L, "2014-08-01 15:10:10").build();
        InWorkingTask inWorkingTask = (InWorkingTask)TaskBuilder.inWorkingTask(11L, "2014-08-01 15:10:10", "2014-08-01 15:30:00").build();
        
        ModifyTaskForm form = new ModifyTaskForm();
        form.status = Status.IN_WORKING;
        form.title = "更新後タイトル";
        form.period = DateUtil.create("2014-08-10 10:15:10");
        form.importance = Importance.B;
        form.detail = "更新後詳細";
        
        // exercise
        service.modify(task, form, inWorkingTask);
        
        // verify
        new Verifications() {{
            List<Task> list = new ArrayList<>();
            
            repository.saveModification(withCapture(list));
            
            assertThat(list.get(0).getId(), is(11L));
            assertThat(list.get(0).getStatus(), is(Status.STOPPED));
        }};
    }

}
