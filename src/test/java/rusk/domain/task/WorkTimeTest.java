package rusk.domain.task;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.Date;

import org.junit.Test;

import rusk.util.DateUtil;

public class WorkTimeTest {

    @Test(expected=IllegalArgumentException.class)
    public void 開始時間が終了時間より後の場合_例外がスローされること() {
        // setup
        Date before = DateUtil.create("2014-01-01 15:00:00");
        Date after = DateUtil.addMilliseconds(before, 1);
        
        // exercise
        new WorkTime(after, before);
    }

    @Test(expected=IllegalArgumentException.class)
    public void 開始時間と終了時間が同じ場合_例外がスローされること() {
        // setup
        Date time = DateUtil.create("2014-01-01 15:00:01");
        
        // exercise
        new WorkTime(time, time);
    }
    
    @Test
    public void 開始時間と終了時間の差分がミリ秒で取得できること() {
        // setup
        Date before = DateUtil.create("2014-01-01 15:00:00");
        Date after = DateUtil.addMilliseconds(before, 3560);
        
        WorkTime workTime = new WorkTime(before, after);
        
        // exercise
        long duration = workTime.getDuration();
        
        // verify
        assertThat(duration, is(3560L));
    }
    
    @Test
    public void 作業時間が重なっている場合_isDuplicateがtrueを返すこと() {
        // setup
        Date before = DateUtil.create("2014-01-01 11:00:00");
        Date middle = DateUtil.addMilliseconds(before, 100);
        Date after = DateUtil.addMilliseconds(middle, 100);
        
        WorkTime time1 = new WorkTime(before, middle);
        WorkTime time2 = new WorkTime(middle, after);
        
        // exercise
        boolean duplicate = time1.isDuplicate(time2);
        
        // verify
        assertThat(duplicate, is(true));
    }
    
    @Test
    public void 作業時間が重なっていない場合_isDuplicateがfalseを返すこと() {
        // setup
        Date before = DateUtil.create("2014-01-01 11:00:00");
        Date middle1 = DateUtil.addMilliseconds(before, 100);
        Date middle2 = DateUtil.addMilliseconds(middle1, 100);
        Date after = DateUtil.addMilliseconds(middle2, 100);
        
        WorkTime time1 = new WorkTime(before, middle1);
        WorkTime time2 = new WorkTime(middle2, after);
        
        // exercise
        boolean duplicate = time1.isDuplicate(time2);
        
        // verify
        assertThat(duplicate, is(false));
    }

    @Test
    public void 開始時間と終了時間が同じ場合_equalsメソッドはtrueを返すこと() {
        // setup
        Date before = DateUtil.create("2014-01-01 11:00:00");
        Date after = DateUtil.addMilliseconds(before, 100);
        
        WorkTime one = new WorkTime(before, after);
        WorkTime other = new WorkTime(before, after);
        
        // verify
        assertThat(one.equals(other), is(true));
    }
}
