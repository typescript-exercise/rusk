package rusk.domain.task;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import rusk.common.util.DateUtil;
import rusk.domain.task.exception.DuplicateWorkTimeException;
import rusk.domain.task.exception.WorkTimeNotFoundException;

public class TaskTest {

    private static final Date DATETIME_1 = DateUtil.create("2014-01-01 15:00:00");
    private static final Date DATETIME_2 = DateUtil.addMilliseconds(DATETIME_1, 100);
    private static final Date DATETIME_3 = DateUtil.addMilliseconds(DATETIME_2, 100);
    private static final Date DATETIME_4 = DateUtil.addMilliseconds(DATETIME_3, 100);
    
    private Task task;
    
    @Before
    public void setup() {
        task = new Task(DATETIME_1) {
            @Override
            public Status getStatus() {
                return null;
            }
            @Override
            public List<Status> getEnableToSwitchStatusList() {
                return null;
            }};
    }
    
    @Test
    public void 作業中の作業時間は_常にNullオブジェクトが返されること() {
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
    
    @Test
    public void 指定した作業時間の更新ができる() {
        // setup
        Date base = DateUtil.create("2014-09-01 00:00:00");
        
        WorkTime workTime1 = createWorkTime(1L, base, 10);
        WorkTime workTime2 = createWorkTime(2L, DateUtil.addSeconds(base, 20), 20);
        
        List<WorkTime> originalWorkTimes = Arrays.asList(workTime1, workTime2);

        task.setWorkTimes(originalWorkTimes);
        
        // exercise
        WorkTime modifiedWorkTime = createWorkTime(workTime2.getId(), DateUtil.addSeconds(base, 100), 100);
        task.modifyWorkTime(modifiedWorkTime);
        
        // verify
        assertThat(task.getWorkTimes(), containsInAnyOrder(workTime1, modifiedWorkTime));
    }
    
    @Test(expected=DuplicateWorkTimeException.class)
    public void 更新しようとした作業時間が_既存の作業時間と重複する場合_例外がスローされること() {
        // setup
        Date base = DateUtil.create("2014-09-01 00:00:00");
        
        WorkTime workTime1 = createWorkTime(1L, base, 10);
        WorkTime workTime2 = createWorkTime(2L, DateUtil.addSeconds(base, 20), 20);
        
        List<WorkTime> originalWorkTimes = Arrays.asList(workTime1, workTime2);

        task.setWorkTimes(originalWorkTimes);
        
        // exercise
        WorkTime modifiedWorkTime = createWorkTime(workTime2.getId(), base, 10);
        task.modifyWorkTime(modifiedWorkTime);
    }
    
    @Test(expected=WorkTimeNotFoundException.class)
    public void 更新しようとした作業時間が存在しない場合_例外がスローされること() {
        // setup
        Date base = DateUtil.create("2014-09-01 00:00:00");
        
        WorkTime workTime1 = createWorkTime(1L, base, 10);
        WorkTime workTime2 = createWorkTime(2L, DateUtil.addSeconds(base, 20), 20);
        
        List<WorkTime> originalWorkTimes = Arrays.asList(workTime1, workTime2);

        task.setWorkTimes(originalWorkTimes);
        
        // exercise
        long unknownId = -1L;
        WorkTime modifiedWorkTime = createWorkTime(unknownId, base, 10);
        
        task.modifyWorkTime(modifiedWorkTime);
    }
    
    private WorkTime createWorkTime(long id, Date base, int durationSec) {
        return WorkTime.deserializeConcludedWorkTime(id, base, DateUtil.addSeconds(base, durationSec), DateUtil.create("2014-01-01 01:01:01"));
    }
    
    @Test
    public void ID指定で作業時間が取得できること() {
        // setup
        WorkTime workTime1 = WorkTime.deserializeConcludedWorkTime(1L, DATETIME_1, DATETIME_2, DATETIME_1);
        WorkTime workTime2 = WorkTime.deserializeConcludedWorkTime(2L, DATETIME_3, DATETIME_4, DATETIME_1);
        
        task.setWorkTimes(Arrays.asList(workTime1, workTime2));
        
        // exercise
        WorkTime selected = task.getWorkTime(1L);
        
        // verify
        assertThat(selected, is(workTime1));
    }
    
    @Test
    public void 指定した作業時間を削除できる() {
        // setup
        WorkTime workTime1 = WorkTime.deserializeConcludedWorkTime(1L, DATETIME_1, DATETIME_2, DATETIME_1);
        WorkTime workTime2 = WorkTime.deserializeConcludedWorkTime(2L, DATETIME_3, DATETIME_4, DATETIME_1);
        
        task.setWorkTimes(Arrays.asList(workTime1, workTime2));
        
        // exercise
        task.removeWorkTime(2L);
        
        // verify
        assertThat(task.getWorkTimes(), contains(workTime1));
    }
    
    @Test(expected=WorkTimeNotFoundException.class)
    public void 削除しようとした作業時間が存在しない場合_例外がスローされる() {
        // setup
        WorkTime workTime1 = WorkTime.deserializeConcludedWorkTime(1L, DATETIME_1, DATETIME_2, DATETIME_1);
        WorkTime workTime2 = WorkTime.deserializeConcludedWorkTime(2L, DATETIME_3, DATETIME_4, DATETIME_1);
        
        task.setWorkTimes(Arrays.asList(workTime1, workTime2));
        
        // exercise
        task.removeWorkTime(3L);
    }
}
