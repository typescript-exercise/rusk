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

import org.dbunit.dataset.IDataSet;
import org.junit.Rule;
import org.junit.Test;

import rusk.common.util.DateUtil;
import rusk.domain.task.form.ModifyWorkTimeForm;

@Fixture(resources="modify-work-time.yaml")
public class ModifyWorkTimeTest {

    @Rule
    public JerseyTestRule rule = new JerseyTestRule();
    @Rule
    public RuskIntegrationDBTester dbTester = new RuskIntegrationDBTester(ModifyTaskTest.class);
    
    @Test
    public void 追加時のテスト() throws Exception {
        // setup
        ModifyWorkTimeForm form = new ModifyWorkTimeForm();
        form.startTime = DateUtil.create("2014-07-12 10:12:14");
        form.endTime = DateUtil.create("2014-07-12 11:20:30");
        
        Entity<ModifyWorkTimeForm> entity = Entity.entity(form, MediaType.APPLICATION_JSON);
        
        // exercise
        rule.getTest().target("task/2/work-time").request().post(entity);
        
        // verify
        IDataSet expected = dbTester.loadDataSet("modify-work-time-add-expected.yaml");
        dbTester.verifyTable("WORK_TIME", expected, "ID");
    }
    
    @Test
    public void 追加したタスクの作業時間が他の作業時間を重複している場合_400エラーが返される() throws Exception {
        // setup
        ModifyWorkTimeForm form = new ModifyWorkTimeForm();
        form.startTime = DateUtil.create("2014-01-01 14:00:00");
        form.endTime = DateUtil.create("2014-01-01 15:00:00");
        
        Entity<ModifyWorkTimeForm> entity = Entity.entity(form, MediaType.APPLICATION_JSON);
        
        // exercise
        Response response = rule.getTest().target("task/2/work-time").request().post(entity);
        
        // verify
        assertThat(response.getStatus(), is(Status.BAD_REQUEST.getStatusCode()));
    }
}
