package rusk.integration_test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.stream.Collectors;

import jp.classmethod.testing.database.Fixture;
import mockit.NonStrictExpectations;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
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
        assertThat(task.getTitle(), is("作業中タスク"));
        
        List<String> uncompletedTaskTitles = taskList.getUncompletedTasks().stream().map(t -> t.getTitle()).collect(Collectors.toList());
        
        assertThat(uncompletedTaskTitles, is(contains("未着手・優先度３", "未着手・優先度２", "中断・優先度１")));
        
        List<String> completedTaskTitles = taskList.getCompletedTasks().stream().map(t -> t.getTitle()).collect(Collectors.toList());

        assertThat(completedTaskTitles, is(contains("今日完了・優先度３", "今日完了・優先度１")));
    }
}
