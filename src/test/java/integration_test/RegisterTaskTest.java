package integration_test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import integration_test.db.RuskIntegrationDBTester;
import integration_test.jersey.JerseyTestRule;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import jp.classmethod.testing.database.Fixture;
import mockit.NonStrictExpectations;

import org.dbunit.dataset.IDataSet;
import org.junit.Rule;
import org.junit.Test;

import rusk.common.util.DateUtil;
import rusk.common.util.Now;
import rusk.domain.task.Importance;
import rusk.domain.task.form.RegisterTaskForm;

/**
 * タスク登録テスト
 */
public class RegisterTaskTest {

    @Rule
    public JerseyTestRule rule = new JerseyTestRule();
    @Rule
    public RuskIntegrationDBTester dbTester = new RuskIntegrationDBTester(RegisterTaskTest.class);
    
    @Test
    @Fixture(resources="register-task.yaml")
    public void test() throws Exception {
        // setup
        RegisterTaskForm form = new RegisterTaskForm();
        
        form.title = "タスク登録";
        form.period = DateUtil.create("2014-01-01 13:14:15");
        form.importance = Importance.B;
        form.detail = "詳細内容";
        
        new NonStrictExpectations(Now.class) {{
            Now.getForRegisteredDate(); result = DateUtil.create("2014-01-01 04:30:00");
        }};
        
        // exercise
        Response response = rule.getTest().target("task").request().post(Entity.entity(form, MediaType.APPLICATION_JSON));
        
        // verify
        assertThat(response.getStatus(), is(Status.CREATED.getStatusCode()));
        assertThat(response.getLocation().getPath().matches("^/rest/task/\\d+$"), is(true));
        
        IDataSet expected = dbTester.loadDataSet("register-task-expected.yaml");
        dbTester.verifyTable("TASK", expected, "ID");
    }
}
