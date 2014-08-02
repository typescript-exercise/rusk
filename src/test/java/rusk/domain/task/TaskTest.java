package rusk.domain.task;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import rusk.util.DateUtil;

public class TaskTest {

    private static final Date DATETIME_1 = DateUtil.create("2014-01-01 15:00:00");
    private static final Date DATETIME_2 = DateUtil.addMilliseconds(DATETIME_1, 100);
    private static final Date DATETIME_3 = DateUtil.addMilliseconds(DATETIME_2, 100);
    private static final Date DATETIME_4 = DateUtil.addMilliseconds(DATETIME_3, 100);
    
    private Task task;
    
    @SuppressWarnings("deprecation")
    @Before
    public void setup() {
        task = new Task();
    }
    
    @Test(expected=DuplicateWorkTimeException.class)
    public void 終了時間が設定されていない作業時間を２つ以上登録できないこと() {
        // setup
        WorkTime time1 = WorkTime.createInWorkingTime(DATETIME_1);
        WorkTime time2 = WorkTime.createInWorkingTime(DATETIME_2);
        
        task.setWorkTimes(Arrays.asList(time1, time2));
        
        // exercise
        task.getWorkTimeInWorking();
    }
    
    @Test
    public void 終了時間が設定されていない作業時間を_作業中の作業時間として取得できること() {
        // setup
        WorkTime time1 = WorkTime.createInWorkingTime(DATETIME_3);
        WorkTime time2 = WorkTime.createConcludedWorkTime(DATETIME_1, DATETIME_2);
        
        task.setWorkTimes(Arrays.asList(time1, time2));
        
        // exercise
        WorkTime actual = task.getWorkTimeInWorking();
        
        // verify
        assertThat(actual, is(time1));
    }
    
    @Test
    public void 終了時間が設定されていない作業時間が存在しない場合_作業中の作業時間はNullオブジェクトが取得できること() {
        // setup
        WorkTime time = WorkTime.createConcludedWorkTime(DATETIME_1, DATETIME_2);
        
        task.addWorkTime(time);
        
        // exercise
        WorkTime actual = task.getWorkTimeInWorking();
        
        // verify
        assertThat(actual, is(instanceOf(NullObjectWorkTime.class)));
    }
    
    @Test(expected=DuplicateWorkTimeException.class)
    public void すでに登録されている作業時間と重複する作業時間を追加した場合_例外がスローされること() {
        // setup
        WorkTime time1 = WorkTime.createConcludedWorkTime(DATETIME_1, DATETIME_2);
        WorkTime time2 = WorkTime.createConcludedWorkTime(DATETIME_2, DATETIME_3);
        
        task.addWorkTime(time1);
        
        // exercise
        task.addWorkTime(time2);
    }
    
    @Test(expected=DuplicateWorkTimeException.class)
    public void 作業時間が重複しているリストをセットすると_例外がスローされること() {
        // setup
        WorkTime time1 = WorkTime.createConcludedWorkTime(DATETIME_1, DATETIME_2);
        WorkTime time2 = WorkTime.createConcludedWorkTime(DATETIME_2, DATETIME_3);
        
        List<WorkTime> workTimes = Arrays.asList(time1, time2);
        
        // exercise
        task.setWorkTimes(workTimes);
    }

    @Test
    public void 作業時間の合計がミリ秒で取得できること() {
        // setup
        List<WorkTime> workTimes = Arrays.asList(WorkTime.createConcludedWorkTime(DATETIME_1, DATETIME_2),
                                                    WorkTime.createConcludedWorkTime(DATETIME_3, DATETIME_4));
        
        task.setWorkTimes(workTimes);
        
        // exercise
        long total = task.getTotalWorkTime();
        
        // verify
        assertThat(total, is(200L));
    }
    
}
