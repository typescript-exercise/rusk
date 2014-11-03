package integration_test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import integration_test.db.RuskIntegrationDBTester;
import integration_test.jersey.JerseyTestRule;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import jp.classmethod.testing.database.Fixture;

import org.junit.Rule;
import org.junit.Test;

import rusk.application.facade.task.DailyHistoryDto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Fixture(resources="inquire-daily-history.yaml")
public class InquireDailyHistoryTest {

    @Rule
    public JerseyTestRule rule = new JerseyTestRule();
    @Rule
    public RuskIntegrationDBTester dbTester = new RuskIntegrationDBTester();

    @Test
    public void 指定した日付に該当するタスクごとの作業時間のサマリ情報が取得できる() throws Exception {
        // exercise
        Response response = rule.getTest().target("task/daily/20140101").request(MediaType.APPLICATION_JSON).get();
        List<DailyHistoryDto> actual = deserialize(response);
        
        // verify
        List<DailyHistoryDto> expected = new ArrayList<>();
        expected.add(dto(1L, "タスク１", 1.5));
        expected.add(dto(2L, "タスク２", 1.25));
        
        int i=0;
        for (DailyHistoryDto e : expected) {
            DailyHistoryDto a = actual.get(i++);
            assertThat(a, is(samePropertyValuesAs(e)));
        }
    }
    
    private static DailyHistoryDto dto(long id, String title, double workTimeSummary) {
        DailyHistoryDto dto = new DailyHistoryDto();
        dto.id = id;
        dto.title = title;
        dto.workTimeSummary = workTimeSummary;
        return dto;
    }
    
    private static List<DailyHistoryDto> deserialize(Response response) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue((InputStream)response.getEntity(), new TypeReference<List<DailyHistoryDto>>() {});
    }
    
    @Test
    public void 該当するタスクが存在しない場合_404のステータスコードが返されること() {
        // exercise
        Response response = rule.getTest().target("task/daily/20141010").request(MediaType.APPLICATION_JSON).get();
        
        // verify
        assertThat(response.getStatus(), is(javax.ws.rs.core.Response.Status.NOT_FOUND.getStatusCode()));
    }
}
