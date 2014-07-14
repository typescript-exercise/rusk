package rusk.persistence.task;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import jp.classmethod.testing.database.Fixture;
import mockit.NonStrictExpectations;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import rusk.domain.task.Importance;
import rusk.domain.task.Status;
import rusk.domain.task.Task;
import rusk.domain.task.WorkTimeRepository;
import rusk.test.db.RuskDBTester;
import rusk.test.db.TestPersistProvider;
import rusk.util.DateUtil;
import rusk.util.Today;

@Fixture(resources="TaskRepositoryImple-fixuture.yaml")
public class TaskRepositoryImplTest {
    
    @Rule
    public TestPersistProvider provider = new TestPersistProvider();
    @Rule
    public RuskDBTester dbTester = new RuskDBTester();
    
    private TaskRepositoryImpl repository;
    private WorkTimeRepository worktimeRepository;
    
    @Before
    public void setup() {
        new NonStrictExpectations(Today.class) {{
            Today.get(); result = DateUtil.create("2014-07-01 12:10:00");
        }};
        
        worktimeRepository = new WorkTimeRepositoryImpl(provider);
        repository = new TaskRepositoryImpl(provider, worktimeRepository);
    }
    
    @Test
    public void データベースに登録されているタスクの情報を_Javaオブジェクトに正しくマッピングされること() {
        // exercise
        Task task = repository.findById(1L);
        
        // verify
        assertThat(task.getId(), is(1L));
        assertThat(task.getTitle(), is("完了１"));
        assertThat(task.getStatus(), is(Status.COMPLETE));
        assertThat(task.getDetail(), is("これは完了タスクです"));
        assertThat(task.getRegisteredDate(), is(DateUtil.create("2014-07-01 12:00:00")));
        assertThat(task.getCompletedDate(), is(DateUtil.create("2014-07-02 11:22:33")));
        assertThat(task.getPriority().getImportance(), is(Importance.S));
        assertThat(task.getPriority().getUrgency().getPeriod(), is(DateUtil.create("2014-07-01 12:50:00")));
        assertThat(task.getWorkTimes().get(0).getStartTime(), is(DateUtil.create("2014-07-01 13:00:00")));
    }
    
    @Test
    public void 作業中のタスクが検索できること() {
        // exercise
        Task task = repository.findTaskInWork();
        
        // verify
        assertThat(task.getId(), is(2L));
    }
    
    @Test
    @Fixture(resources="TaskRepositoryImple-fixuture-作業中タスクなし.yaml")
    public void 作業中のタスクが存在しない場合_nullが返されること() {
        // exercise
        Task task = repository.findTaskInWork();
        
        // verify
        assertThat(task, is(nullValue()));
    }
    
    @Test
    public void 未完了のタスクが検索できること() {
        // exercise
        List<Task> uncompleteTasks = repository.findUncompletedTasks();
        
        // verify
        List<Long> ids = uncompleteTasks.stream().map(task -> task.getId()).collect(Collectors.toList());
        assertThat(ids, is(containsInAnyOrder(3L, 4L, 5L, 6L)));
    }
    
    @Test
    @Fixture(resources="TaskRepositoryImple-fixuture-未完了タスクなし.yaml")
    public void 未完了のタスクが存在しない場合_空のリストが返されること() {
        // exercise
        List<Task> uncompleteTasks = repository.findUncompletedTasks();
        
        // verify
        assertThat(uncompleteTasks, is(empty()));
    }
    
    @Test
    public void 指定日に完了になったタスクが検索できること() {
        // setup
        Date date = DateUtil.create("2014-07-03 12:00:00");
        
        // exercise
        List<Task> completedTasks = repository.findCompleteTasks(date);
        
        // verify
        List<Long> ids = completedTasks.stream().map(task -> task.getId()).collect(Collectors.toList());
        assertThat(ids, is(containsInAnyOrder(7L, 8L)));
    }
    
    @Test
    public void 指定日に完了になったタスクが存在しない場合_空のリストが返されること() {
        // setup
        Date date = DateUtil.create("2014-07-01 12:00:00");
        
        // exercise
        List<Task> completedTasks = repository.findCompleteTasks(date);
        
        // verify
        assertThat(completedTasks, is(empty()));
    }
}
