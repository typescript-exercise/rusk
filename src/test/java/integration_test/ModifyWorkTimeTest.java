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
        dbTester.verifyTable("WORK_TIME", expected, "ID", "UPDATE_DATE");
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
    
    @Test
    public void 更新時のテスト() throws Exception {
        // setup
        ModifyWorkTimeForm form = new ModifyWorkTimeForm();
        form.startTime = DateUtil.create("2014-01-01 15:10:00");
        form.endTime = DateUtil.create("2014-01-01 16:20:00");
        form.lastUpdateDate = DateUtil.create("2014-01-01 16:30:02");
        
        Entity<ModifyWorkTimeForm> entity = Entity.entity(form, MediaType.APPLICATION_JSON);
        
        // exercise
        rule.getTest().target("task/1/work-time/1").request().put(entity);
        
        // verify
        IDataSet expected = dbTester.loadDataSet("modify-work-time-modify-expected.yaml");
        dbTester.verifyTable("WORK_TIME", expected, "UPDATE_DATE");
    }
    
    @Test
    public void 更新しようとした作業時間が同時更新されていた場合は409が返されること() throws Exception {
        // setup
        ModifyWorkTimeForm form = new ModifyWorkTimeForm();
        form.startTime = DateUtil.create("2014-01-01 15:10:00");
        form.endTime = DateUtil.create("2014-01-01 16:20:00");
        form.lastUpdateDate = DateUtil.create("2014-01-01 16:30:00");
        
        Entity<ModifyWorkTimeForm> entity = Entity.entity(form, MediaType.APPLICATION_JSON);
        
        // exercise
        Response response = rule.getTest().target("task/1/work-time/1").request().put(entity);
        
        // verify
        assertThat(response.getStatus(), is(Status.CONFLICT.getStatusCode()));
    }
    
    @Test
    public void 削除時のテスト() throws Exception {
        // exercise
        rule.getTest().target("task/1/work-time/1").request().delete();
        
        // verify
        IDataSet expected = dbTester.loadDataSet("modify-work-time-remove-expected.yaml");
        dbTester.verifyTable("WORK_TIME", expected);
    }
    
    @Test
    public void 存在しない作業時間IDを削除しようとした場合_404を返すこと() throws Exception {
        // exercise
        Response response = rule.getTest().target("task/1/work-time/50").request().delete();
        
        // verify
        assertThat(response.getStatus(), is(Status.NOT_FOUND.getStatusCode()));
    }
    
}
