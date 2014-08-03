package rusk.integration_test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import jp.classmethod.testing.database.Fixture;
import mockit.NonStrictExpectations;

import org.dbunit.dataset.IDataSet;
import org.junit.Rule;
import org.junit.Test;

import rusk.domain.task.StatusForm;
import rusk.domain.task.SwitchStatusForm;
import rusk.integration_test.db.RuskIntegrationDBTester;
import rusk.integration_test.jersey.JerseyTestRule;
import rusk.util.DateUtil;
import rusk.util.Now;

@Fixture(resources="switch-task-status.yaml")
public class SwitchTaskStatusTest {

    @Rule
    public JerseyTestRule rule = new JerseyTestRule();
    @Rule
    public RuskIntegrationDBTester dbTester = new RuskIntegrationDBTester(SwitchTaskStatusTest.class);
    
    @Test
    public void test() throws Exception {
        // setup
        new NonStrictExpectations(Now.class) {{
            Now.getForEndTime(); result = DateUtil.create("2014-07-10 12:40:00");
            Now.getForStartTime(); result = DateUtil.create("2014-07-10 12:40:01");
        }};
        
        SwitchStatusForm form = new SwitchStatusForm();
        form.status = StatusForm.IN_WORKING;
        Entity<SwitchStatusForm> entiry = Entity.entity(form, MediaType.APPLICATION_JSON);
        
        // exercise
        rule.getTest().target("task/3/status").request().put(entiry);
        
        // verify
        IDataSet expected = dbTester.loadDataSet("switch-task-status-expected.yaml");
        dbTester.verifyTable("TASK", expected);
        dbTester.verifyTable("WORK_TIME", expected, "ID");
    }
}
