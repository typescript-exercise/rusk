package rusk.persistence.task;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import jp.classmethod.testing.database.Fixture;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import rusk.domain.task.WorkTime;
import rusk.test.db.RuskDBTester;
import rusk.test.db.TestPersistProvider;
import rusk.util.DateUtil;

@Fixture(resources="WorkTimeRepositoryImpl-fixture.yaml")
public class WorkTimeRepositoryImplTest {
    @Rule
    public TestPersistProvider provider = new TestPersistProvider();
    @Rule
    public RuskDBTester dbTester = new RuskDBTester();

    private WorkTimeRepositoryImpl repository;
    
    @Before
    public void setup() {
        repository = new WorkTimeRepositoryImpl(provider);
    }
    
    @Test
    public void test() {
        // exercise
        List<WorkTime> workTimes = repository.findByTaskId(1);
        
        // verify
        List<Date> startTimes = workTimes.stream().map(time -> time.getStartTime()).collect(Collectors.toList());
        
        assertThat(startTimes, is(contains(
                    DateUtil.create("2014-01-01 10:00:00"),
                    DateUtil.create("2014-01-01 11:00:01"),
                    DateUtil.create("2014-01-01 11:30:10"),
                    DateUtil.create("2014-01-01 13:30:00")
                )));
    }

}
