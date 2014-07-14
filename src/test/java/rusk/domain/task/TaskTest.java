package rusk.domain.task;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import rusk.util.DateUtil;

@SuppressWarnings("deprecation")
public class TaskTest {

    @Test(expected=DuplicateWorkTimeException.class)
    public void すでに登録されている作業時間と重複する作業時間を追加した場合_例外がスローされること() {
        // setup
        Date before = DateUtil.create("2014-01-01 11:00:00");
        Date middle = DateUtil.addMilliseconds(before, 100);
        Date after = DateUtil.addMilliseconds(middle, 100);
        
        WorkTime time1 = new WorkTime(before, middle);
        WorkTime time2 = new WorkTime(middle, after);
        
        Task task = new Task();
        task.add(time1);
        
        // exercise
        task.add(time2);
    }
    
    @Test(expected=DuplicateWorkTimeException.class)
    public void 作業時間が重複しているリストをセットすると_例外がスローされること() {
        // setup
        Date before = DateUtil.create("2014-01-01 11:00:00");
        Date middle = DateUtil.addMilliseconds(before, 100);
        Date after = DateUtil.addMilliseconds(middle, 100);
        
        WorkTime time1 = new WorkTime(before, middle);
        WorkTime time2 = new WorkTime(middle, after);
        
        List<WorkTime> workTimes = Arrays.asList(time1, time2);
        
        Task task = new Task();
        task.setWorkTimes(workTimes);
    }

    @Test
    public void 作業時間の合計がミリ秒で取得できること() {
        // setup
        Date time1 = DateUtil.create("2014-01-01 11:00:00");
        Date time2 = DateUtil.addMilliseconds(time1, 111);
        Date time3 = DateUtil.addMilliseconds(time2, 1);
        Date time4 = DateUtil.addMilliseconds(time3, 444);
        
        List<WorkTime> workTimes = Arrays.asList(new WorkTime(time1, time2), new WorkTime(time3, time4));
        
        Task task = new Task();
        task.setWorkTimes(workTimes);
        
        // exercise
        long total = task.getTotalWorkTime();
        
        // verify
        assertThat(total, is(555L));
    }
    
}
