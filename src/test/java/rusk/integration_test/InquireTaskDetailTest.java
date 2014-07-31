package rusk.integration_test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static rusk.test.matcher.RuskMatchers.*;

import java.util.Date;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import jp.classmethod.testing.database.Fixture;
import mockit.NonStrictExpectations;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import rusk.domain.task.Importance;
import rusk.domain.task.Status;
import rusk.domain.task.Task;
import rusk.domain.task.TaskBuilder;
import rusk.integration_test.db.RuskIntegrationDBTester;
import rusk.integration_test.jersey.JerseyTestRule;
import rusk.util.DateUtil;
import rusk.util.Now;

@Fixture(resources="inquire-task-detail.yaml")
public class InquireTaskDetailTest {

    @Rule
    public JerseyTestRule rule = new JerseyTestRule();
    @Rule
    public RuskIntegrationDBTester dbTester = new RuskIntegrationDBTester();
    
    private Date now = DateUtil.create("2014-01-03 15:30:00");
    
    @Before
    public void setup() {
        new NonStrictExpectations(Now.class) {{
            Now.get(); result = now;
        }};
    }
    
    @Test
    public void 指定したIDのタスクが取得できること() {
        // exercise
        Task task = rule.getTest().target("task/2").request(MediaType.APPLICATION_JSON).get(Task.class);
        
        // verify
        Task expected = new TaskBuilder(2L, "2014-01-02 15:00:00", "2014-01-03 14:10:00")
                                .title("対象タスク")
                                .status(Status.COMPLETE)
                                .detail("このタスクは対象です。")
                                .priority("2014-01-03 18:00:00", Importance.A)
                                .addWorkTime("2014-01-02 17:00:00", "2014-01-02 17:10:00")
                                .addWorkTime("2014-01-03 13:00:00", "2014-01-03 14:10:00")
                                .build();
        
        assertThat(task, is(sameTaskOf(expected)));
    }
    
    @Test
    public void 指定したIDのタスクが存在しない場合_404のステータスコードが返されること() {
        // exercise
        Response response = rule.getTest().target("task/999").request(MediaType.APPLICATION_JSON).get();
        
        // verify
        assertThat(response.getStatus(), is(javax.ws.rs.core.Response.Status.NOT_FOUND.getStatusCode()));
    }
}
