package rusk.persistence.task;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static rusk.test.matcher.RuskMatchers.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import jp.classmethod.testing.database.Fixture;
import mockit.NonStrictExpectations;

import org.dbunit.dataset.IDataSet;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import rusk.domain.EntityNotFoundException;
import rusk.domain.task.Importance;
import rusk.domain.task.Priority;
import rusk.domain.task.Task;
import rusk.domain.task.TaskBuilder;
import rusk.domain.task.TaskFactory;
import rusk.service.task.RegisterTaskForm;
import rusk.test.db.RuskDBTester;
import rusk.test.db.TestPersistProvider;
import rusk.util.DateUtil;
import rusk.util.Now;

@Fixture(resources="TaskRepositoryImple-fixuture.yaml")
public class TaskRepositoryImplTest {
    private static final Date DATETIME_1 = DateUtil.create("2014-07-01 14:10:00");
    private static final Date DATETIME_2 = DateUtil.addMilliseconds(DATETIME_1, 100);
    private static final Date DATETIME_3 = DateUtil.addMilliseconds(DATETIME_2, 100);
    private static final Date DATETIME_4 = DateUtil.addMilliseconds(DATETIME_3, 100);
    
    @Rule
    public TestPersistProvider provider = new TestPersistProvider();
    @Rule
    public RuskDBTester dbTester = new RuskDBTester(TaskRepositoryImplTest.class);
    
    private TaskRepositoryImpl repository;
    
    @Before
    public void setup() {
        repository = new TaskRepositoryImpl(provider);
    }
    
    @Test
    public void データベースに登録されているタスクの情報を_Javaオブジェクトに正しくマッピングされること() {
        // exercise
        Task task = repository.inquireById(1L);
        
        // verify
        Task expected = TaskBuilder.completedTask(1L, "2014-07-01 12:00:00", "2014-07-02 11:22:33")
                                .title("完了１")
                                .detail("これは完了タスクです")
                                .priority("2014-07-01 12:50:00", Importance.S)
                                .addWorkTime("2014-07-01 13:00:00", "2014-07-01 13:50:00")
                                .build();
        
        assertThat(task, is(sameTaskOf(expected)));
    }
    
    @Test(expected=EntityNotFoundException.class)
    public void 指定したIDのタスクがデータベースに存在しない場合_例外がスローされること() {
        // exercise
        repository.inquireById(-1L);
    }
    
    @Test
    public void 作業中のタスクが検索できること() {
        // exercise
        Task task = repository.inquireTaskInWorking();
        
        // verify
        assertThat(task.getId(), is(2L));
    }
    
    @Test
    @Fixture(resources="TaskRepositoryImple-fixuture-作業中タスクなし.yaml")
    public void 作業中のタスクが存在しない場合_nullが返されること() {
        // exercise
        Task task = repository.inquireTaskInWorking();
        
        // verify
        assertThat(task, is(nullValue()));
    }
    
    @Test
    public void 未完了のタスクが検索できること() {
        // exercise
        List<Task> uncompleteTasks = repository.inquireUncompletedTasks();
        
        // verify
        List<Long> ids = uncompleteTasks.stream().map(task -> task.getId()).collect(Collectors.toList());
        assertThat(ids, is(containsInAnyOrder(3L, 4L, 5L, 6L)));
    }
    
    @Test
    @Fixture(resources="TaskRepositoryImple-fixuture-未完了タスクなし.yaml")
    public void 未完了のタスクが存在しない場合_空のリストが返されること() {
        // exercise
        List<Task> uncompleteTasks = repository.inquireUncompletedTasks();
        
        // verify
        assertThat(uncompleteTasks, is(empty()));
    }
    
    @Test
    public void 指定日に完了になったタスクが検索できること() {
        // setup
        Date date = DateUtil.create("2014-07-03 12:00:00");
        
        // exercise
        List<Task> completedTasks = repository.inquireCompleteTasks(date);
        
        // verify
        List<Long> ids = completedTasks.stream().map(task -> task.getId()).collect(Collectors.toList());
        assertThat(ids, is(containsInAnyOrder(7L, 8L)));
    }
    
    @Test
    public void 指定日に完了になったタスクが存在しない場合_空のリストが返されること() {
        // setup
        Date date = DateUtil.create("2014-07-01 12:00:00");
        
        // exercise
        List<Task> completedTasks = repository.inquireCompleteTasks(date);
        
        // verify
        assertThat(completedTasks, is(empty()));
    }
    
    @Test
    @Fixture(resources="TaskRepositoryImple-fixuture-タスク登録.yaml")
    public void 指定したタスクがデータベースに登録されること() throws Exception {
        // setup
        new NonStrictExpectations(Now.class) {{
            Now.getForRegisteredDate(); returns(DATETIME_1);
        }};
        
        RegisterTaskForm form = new RegisterTaskForm();
        form.setTitle("タスク登録");
        form.setImportance(Importance.A);
        form.setPeriod(DateUtil.create("2014-07-03 09:00:00"));
        form.setDetail("詳細");
        
        Task task = TaskFactory.create(form);
        
        // exercise
        repository.register(task);
        
        // verify
        IDataSet expected = dbTester.loadDataSet("TaskRepositoryImple-fixuture-タスク登録-expected.yaml");
        
        dbTester.verifyTable("TASK", expected, "ID");
    }
    
    @Test
    @Fixture(resources="TaskRepositoryImple-fixuture-タスク削除.yaml")
    public void 指定したタスクが削除できること() throws Exception {
        // exercise
        repository.remove(2L);
        
        // verify
        IDataSet expected = dbTester.loadDataSet("TaskRepositoryImple-fixuture-タスク削除-expected.yaml");
        
        dbTester.verifyTable("TASK", expected);
        dbTester.verifyTable("WORK_TIME", expected);
    }

    
    @Test
    public void 指定したタスクが更新できること() throws Exception {
        // setup
        new NonStrictExpectations(Now.class) {{
            Now.getForUrgency(); returns(DATETIME_1, DATETIME_4);
        }};
        
        Task originalTask = repository.inquireById(1L);
        
        originalTask.setDetail("詳細更新");
        originalTask.setTitle("タイトル更新");
        originalTask.setPriority(Priority.of(DATETIME_4, Importance.C));
        Task switchedTask = originalTask.switchToInWorkingTask();
        
        // exercise
        repository.saveModification(switchedTask);
        
        // verify
        Task savedTask = repository.inquireById(1L);
        
        assertThat(savedTask, is(sameTaskOf(switchedTask)));
    }
    
    @Test
    public void 指定したタスクを作業中に更新できること() throws Exception {
        // setup
        new NonStrictExpectations(Now.class) {{
            Now.getForUrgency(); returns(DATETIME_1, DATETIME_4);
        }};
        
        Task originalTask = repository.inquireById(5L);
        
        originalTask.setDetail("詳細更新");
        originalTask.setTitle("タイトル更新");
        originalTask.setPriority(Priority.of(DATETIME_4, Importance.C));
        Task switchedTask = originalTask.switchToInWorkingTask();
        
        // exercise
        repository.saveModification(switchedTask);
        
        // verify
        Task savedTask = repository.inquireById(5L);
        
        assertThat(savedTask, is(sameTaskOf(switchedTask)));
    }
    
    @Test
    public void 指定したタスクを完了に更新できること() throws Exception {
        // setup
        new NonStrictExpectations(Now.class) {{
            Now.getForUrgency(); returns(DATETIME_1, DATETIME_4);
        }};
        
        Task originalTask = repository.inquireById(5L);
        
        originalTask.setDetail("詳細更新ー完了");
        originalTask.setTitle("タイトル更新―完了");
        originalTask.setPriority(Priority.of(DATETIME_4, Importance.B));
        Task switchedTask = originalTask.switchToCompletedTask();
        
        // exercise
        repository.saveModification(switchedTask);
        
        // verify
        Task savedTask = repository.inquireById(5L);
        
        assertThat(savedTask, is(sameTaskOf(switchedTask)));
    }
}
