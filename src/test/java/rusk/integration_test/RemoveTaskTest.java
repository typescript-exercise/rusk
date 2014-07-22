package rusk.integration_test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import jp.classmethod.testing.database.Fixture;

import org.dbunit.dataset.IDataSet;
import org.junit.Rule;
import org.junit.Test;

import rusk.integration_test.db.RuskIntegrationDBTester;
import rusk.integration_test.jersey.JerseyTestRule;

/**
 * タスク削除のテスト
 */
@Fixture(resources="remove-task.yaml")
public class RemoveTaskTest {

    @Rule
    public JerseyTestRule rule = new JerseyTestRule();
    @Rule
    public RuskIntegrationDBTester dbTester = new RuskIntegrationDBTester(RemoveTaskTest.class);

    @Test
    public void 指定されたタスクIDが存在する場合_タスクが削除されること() throws Exception {
        // exercise
        Response response = rule.getTest().target("task/2").request().delete();
        
        // verify
        assertThat(response.getStatus(), is(Status.NO_CONTENT.getStatusCode()));
        
        IDataSet expected = dbTester.loadDataSet("remove-task-expected.yaml");
        dbTester.verifyTable("TASK", expected);
        dbTester.verifyTable("WORK_TIME", expected);
    }
    
    @Test
    public void 指定されたタスクIDが存在しない場合_404が返されること() throws Exception {
        // exercise
        Response response = rule.getTest().target("task/999").request().delete();
        
        // verify
        assertThat(response.getStatus(), is(Status.NOT_FOUND.getStatusCode()));
    }
}
