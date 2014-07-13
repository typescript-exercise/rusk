package rusk.integration_test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.stream.Collectors;

import jp.classmethod.testing.database.Fixture;
import mockit.NonStrictExpectations;

import org.junit.Rule;
import org.junit.Test;

import rusk.domain.list.TaskList;
import rusk.domain.task.Task;
import rusk.integration_test.db.RuskDBTester;
import rusk.util.DateUtil;
import rusk.util.Today;

/**
 * タスクリストの問い合わせテスト。
 */
public class InquireTaskListTest {
    
    @Rule
    public JerseyTestRule rule = new JerseyTestRule();
    @Rule
    public RuskDBTester dbTester = new RuskDBTester();
    
    @Test
    @Fixture(resources="inquire-task-list.yaml")
    public void test() {
        // setup
        new NonStrictExpectations(Today.class) {{
            Today.get(); result = DateUtil.create("2014-07-15 12:00:00");
        }};
        
        // exercise
        TaskList taskList = rule.getTest().target("task-list").request().get(TaskList.class);
        
        // verify
        Task task = taskList.getTaskInWorking();
        assertThat(task.getId(), is(1L));
        
        List<Long> uncompletedTaskIds = taskList.getUncompleteTasks().stream().map(t -> t.getId()).collect(Collectors.toList());

        assertThat(uncompletedTaskIds, is(contains(3L, 2L, 4L, 8L)));
        
        List<Long> completedTaskIds = taskList.getCompleteTasks().stream().map(t -> t.getId()).collect(Collectors.toList());
        
        assertThat(completedTaskIds, is(contains(7L, 5L)));
    }
}