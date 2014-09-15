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
import rusk.domain.task.Status;
import rusk.domain.task.form.SwitchStatusForm;

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
            Now.getForTaskUpdateDate(); returns(DateUtil.createTimestamp("2014-07-10 12:40:02"), DateUtil.createTimestamp("2014-07-10 12:40:03"));
            Now.getForWorkTimeUpdateDate(); result = DateUtil.createTimestamp("2014-07-10 12:40:04");
        }};
        
        SwitchStatusForm form = new SwitchStatusForm();
        form.status = Status.IN_WORKING;
        form.lastUpdateDate = DateUtil.create("2014-07-10 12:00:00");
        Entity<SwitchStatusForm> entiry = Entity.entity(form, MediaType.APPLICATION_JSON);
        
        // exercise
        rule.getTest().target("task/3/status").request().put(entiry);
        
        // verify
        IDataSet expected = dbTester.loadDataSet("switch-task-status-expected.yaml");
        dbTester.verifyTable("TASK", expected);
        dbTester.verifyTable("WORK_TIME", expected, "ID");
    }
}
