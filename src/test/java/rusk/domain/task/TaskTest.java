package rusk.domain.task;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import mockit.NonStrictExpectations;

import org.junit.Test;

import rusk.util.DateUtil;
import rusk.util.Today;

@SuppressWarnings("deprecation")
public class TaskTest {

    private static final Date DATETIME_1 = DateUtil.create("2014-01-01 15:00:00");
    private static final Date DATETIME_2 = DateUtil.addMilliseconds(DATETIME_1, 100);
    private static final Date DATETIME_3 = DateUtil.addMilliseconds(DATETIME_2, 100);
    private static final Date DATETIME_4 = DateUtil.addMilliseconds(DATETIME_3, 100);
    
    @Test
    public void 完了済みのタスクの状態を作業中に更新した場合_タスクに完了時間がnullになっていること() {
        // setup
        new NonStrictExpectations(Today.class) {{
            Today.get(); returns(DATETIME_1, DATETIME_4);
        }};
        
        Task task = new Task();
        task.setStatus(Status.COMPLETE);
        task.setCompletedDate(DATETIME_2);
        
        // exercise
        task.switchStatus(Status.IN_WORKING);
        
        // verify
        assertThat(task.getCompletedDate(), is(nullValue()));
    }
    
    @Test
    public void 中断中のタスクの状態を完了に更新した場合_タスクに完了時間がセットされること() {
        // setup
        new NonStrictExpectations(Today.class) {{
            Today.get(); returns(DATETIME_1, DATETIME_4);
        }};
        
        Task task = new Task();
        task.switchStatus(Status.STOPPED);
        
        // exercise
        task.switchStatus(Status.COMPLETE);
        
        // verify
        assertThat(task.getStatus(), is(Status.COMPLETE));
        assertThat(task.getCompletedDate(), is(DATETIME_4));
    }
    
    @Test
    public void 作業中のタスクの状態を完了に更新した場合_作業中だった作業時間に終了時間がセットされ_タスクに完了時間がセットされること() {
        // setup
        new NonStrictExpectations(Today.class) {{
            Today.get(); returns(DATETIME_1, DATETIME_3);
        }};
        
        Task task = new Task();
        task.switchStatus(Status.IN_WORKING);
        
        // exercise
        task.switchStatus(Status.COMPLETE);
        
        // verify
        assertThat(task.getStatus(), is(Status.COMPLETE));
        
        List<WorkTime> workTimes = task.getWorkTimes();
        assertThat(workTimes.size(), is(1));
        
        WorkTime timeInWorking = workTimes.get(0);
        assertThat(timeInWorking.getStartTime(), is(DATETIME_1));
        assertThat(timeInWorking.getEndTime(), is(DATETIME_3));
        
        assertThat(task.getCompletedDate(), is(DATETIME_3));
    }
    
    @Test
    public void タスクの状態を中断に更新した場合_作業中だった作業時間に終了時間がセットされること() {
        // setup
        new NonStrictExpectations(Today.class) {{
            Today.get(); returns(DATETIME_1, DATETIME_3);
        }};
        
        Task task = new Task();
        task.switchStatus(Status.IN_WORKING);
        
        // exercise
        task.switchStatus(Status.STOPPED);
        
        // verify
        assertThat(task.getStatus(), is(Status.STOPPED));
        
        List<WorkTime> workTimes = task.getWorkTimes();
        assertThat(workTimes.size(), is(1));
        
        WorkTime timeInWorking = workTimes.get(0);
        assertThat(timeInWorking.getStartTime(), is(DATETIME_1));
        assertThat(timeInWorking.getEndTime(), is(DATETIME_3));
    }
    
    @Test
    public void タスクの状態を作業中に更新した場合_開始時間だけが設定された作業時間がタスクに追加されること() {
        // setup
        new NonStrictExpectations(Today.class) {{
            Today.get(); result = DATETIME_1;
        }};
        
        Task task = new Task();
        
        // exercise
        task.switchStatus(Status.IN_WORKING);
        
        // verify
        assertThat(task.getStatus(), is(Status.IN_WORKING));
        
        List<WorkTime> workTimes = task.getWorkTimes();
        assertThat(workTimes.size(), is(1));
        
        WorkTime timeInWorking = workTimes.get(0);
        assertThat(timeInWorking.getStartTime(), is(DATETIME_1));
        assertThat(timeInWorking.hasEndTime(), is(false));
    }
    
    @Test(expected=DuplicateWorkTimeException.class)
    public void 終了時間が設定されていない作業時間を２つ以上登録できないこと() {
        // setup
        WorkTime time1 = new WorkTime(DATETIME_1);
        WorkTime time2 = new WorkTime(DATETIME_2);
        
        Task task = new Task();
        task.setWorkTimes(Arrays.asList(time1, time2));
        
        // exercise
        task.getWorkTimeInWorking();
    }
    
    @Test
    public void 終了時間が設定されていない作業時間を_作業中の作業時間として取得できること() {
        // setup
        WorkTime time1 = new WorkTime(DATETIME_3);
        WorkTime time2 = new WorkTime(DATETIME_1, DATETIME_2);
        
        Task task = new Task();
        task.setWorkTimes(Arrays.asList(time1, time2));
        
        // exercise
        WorkTime actual = task.getWorkTimeInWorking();
        
        // verify
        assertThat(actual, is(time1));
    }
    
    @Test
    public void 終了時間が設定されていない作業時間が存在しない場合_作業中の作業時間はNullオブジェクトが取得できること() {
        // setup
        WorkTime time = new WorkTime(DATETIME_1, DATETIME_2);
        
        Task task = new Task();
        task.addWorkTime(time);
        
        // exercise
        WorkTime actual = task.getWorkTimeInWorking();
        
        // verify
        assertThat(actual, is(instanceOf(NullObjectWorkTime.class)));
    }
    
    @Test(expected=DuplicateWorkTimeException.class)
    public void すでに登録されている作業時間と重複する作業時間を追加した場合_例外がスローされること() {
        // setup
        WorkTime time1 = new WorkTime(DATETIME_1, DATETIME_2);
        WorkTime time2 = new WorkTime(DATETIME_2, DATETIME_3);
        
        Task task = new Task();
        task.addWorkTime(time1);
        
        // exercise
        task.addWorkTime(time2);
    }
    
    @Test(expected=DuplicateWorkTimeException.class)
    public void 作業時間が重複しているリストをセットすると_例外がスローされること() {
        // setup
        WorkTime time1 = new WorkTime(DATETIME_1, DATETIME_2);
        WorkTime time2 = new WorkTime(DATETIME_2, DATETIME_3);
        
        List<WorkTime> workTimes = Arrays.asList(time1, time2);
        
        Task task = new Task();
        
        // exercise
        task.setWorkTimes(workTimes);
    }

    @Test
    public void 作業時間の合計がミリ秒で取得できること() {
        // setup
        List<WorkTime> workTimes = Arrays.asList(new WorkTime(DATETIME_1, DATETIME_2), new WorkTime(DATETIME_3, DATETIME_4));
        
        Task task = new Task();
        task.setWorkTimes(workTimes);
        
        // exercise
        long total = task.getTotalWorkTime();
        
        // verify
        assertThat(total, is(200L));
    }
    
}
