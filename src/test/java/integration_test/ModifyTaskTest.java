package integration_test;

import integration_test.db.RuskIntegrationDBTester;
import integration_test.jersey.JerseyTestRule;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import jp.classmethod.testing.database.Fixture;
import mockit.NonStrictExpectations;

import org.dbunit.dataset.IDataSet;
import org.junit.Rule;
import org.junit.Test;

import rusk.common.util.DateUtil;
import rusk.common.util.Now;
import rusk.domain.task.Importance;
import rusk.domain.task.Status;
import rusk.domain.task.form.ModifyTaskForm;

/**
 * タスク更新テスト
 */
public class ModifyTaskTest {

    @Rule
    public JerseyTestRule rule = new JerseyTestRule();
    @Rule
    public RuskIntegrationDBTester dbTester = new RuskIntegrationDBTester(ModifyTaskTest.class);
    
    @Test
    @Fixture(resources="modify-task.yaml")
    public void test() throws Exception {
        // setup
        new NonStrictExpectations(Now.class) {{
            Now.getForCompletedDate(); result = DateUtil.create("2014-08-10 12:00:01");
            Now.getForUpdateDate(); result = DateUtil.createTimestamp("2014-08-10 12:00:02");
        }};
        
        ModifyTaskForm form = new ModifyTaskForm();
        form.title = "更新後タイトル";
        form.status = Status.COMPLETE;
        form.period = DateUtil.create("2014-08-10 10:15:10");
        form.importance = Importance.B;
        form.detail = "更新後詳細";
        form.lastUpdateDate = DateUtil.create("2014-08-10 10:00:00");
        
        Entity<ModifyTaskForm> entity = Entity.entity(form, MediaType.APPLICATION_JSON);
        
        // exercise
        rule.getTest().target("task/2").request().put(entity);
        
        // verify
        IDataSet expected = dbTester.loadDataSet("modify-task-expected.yaml");
        dbTester.verifyTable("TASK", expected);
    }
}
