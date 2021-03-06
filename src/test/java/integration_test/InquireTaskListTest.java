package integration_test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import integration_test.db.RuskIntegrationDBTester;
import integration_test.jersey.JerseyTestRule;

import java.util.List;
import java.util.stream.Collectors;

import jp.classmethod.testing.database.Fixture;
import mockit.NonStrictExpectations;

import org.junit.Rule;
import org.junit.Test;

import rusk.common.util.DateUtil;
import rusk.common.util.Now;
import rusk.domain.task.Task;
import rusk.domain.task.TaskList;

/**
 * タスクリストの問い合わせテスト。
 */
public class InquireTaskListTest {
    
    @Rule
    public JerseyTestRule rule = new JerseyTestRule();
    @Rule
    public RuskIntegrationDBTester dbTester = new RuskIntegrationDBTester();
    
    @Test
    @Fixture(resources="inquire-task-list.yaml")
    public void test() {
        // setup
        new NonStrictExpectations(Now.class) {{
            Now.getForUrgency(); result = DateUtil.create("2014-07-15 12:10:00");
            Now.getForInquireCompletedTaskInToday(); result = DateUtil.create("2014-07-15 12:00:00");
        }};
        
        // exercise
        TaskList taskList = rule.getTest().target("task-list").request().get(TaskList.class);
        
        // verify
        Task task = taskList.taskInWorking;
        assertThat(task.getId(), is(1L));
        
        List<Long> uncompletedTaskIds = taskList.uncompleteTasks.stream().map(t -> t.getId()).collect(Collectors.toList());

        assertThat(uncompletedTaskIds, is(contains(3L, 2L, 4L, 8L)));
        
        List<Long> completedTaskIds = taskList.completeTasks.stream().map(t -> t.getId()).collect(Collectors.toList());
        
        assertThat(completedTaskIds, is(contains(7L, 5L)));
    }
}
