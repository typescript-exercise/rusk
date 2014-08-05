package rusk.domain.task;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import rusk.common.util.DateUtil;

@RunWith(Enclosed.class)
public class WorkTimeTest {
    
    private static final Date DATETIME_1 = DateUtil.create("2014-01-01 15:00:00");
    private static final Date DATETIME_2 = DateUtil.addMilliseconds(DATETIME_1, 100);
    private static final Date DATETIME_3 = DateUtil.addMilliseconds(DATETIME_2, 100);
    private static final Date DATETIME_4 = DateUtil.addMilliseconds(DATETIME_3, 100);

    public static class 終了時間が設定されていない場合 {
        private WorkTime time;
        
        @Before
        public void setup() {
            time = WorkTime.createInWorkingTime(DATETIME_1);
        }
        
        @Test
        public void 終了時間を確認するメソッドはfalseを返すこと() {
            // exercose
            boolean actual = time.hasEndTime();
            
            // verify
            assertThat(actual, is(false));
        }

        @Test
        public void 作業時間が重なっている場合_isDuplicateがtrueを返すこと() {
            // setup
            WorkTime time2 = WorkTime.createConcludedWorkTime(DATETIME_2, DATETIME_3);
            
            // exercise
            boolean duplicate = time.isDuplicate(time2);
            
            // verify
            assertThat(duplicate, is(true));
        }
        
        @Test
        public void 作業時間が重なっていない場合_isDuplicateがfalseを返すこと() {
            // setup
            WorkTime time1 = WorkTime.createInWorkingTime(DATETIME_4);
            WorkTime time2 = WorkTime.createConcludedWorkTime(DATETIME_1, DATETIME_2);
            
            // exercise
            boolean duplicate = time1.isDuplicate(time2);
            
            // verify
            assertThat(duplicate, is(false));
        }
        
        @Test
        public void 終了時間を取得すると_nullが返されること() {
            // exercise
            Date endTime = time.getEndTime();
            
            // verify
            assertThat(endTime, is(nullValue()));
        }
        
        @Test
        public void 作業時間をミリ秒で取得すると_0が返されること() {
            // exercise
            long duration = time.getDuration();
            
            // verify
            assertThat(duration, is(0L));
        }
    }
    
    public static class 終了時間が設定されている場合 {
        
        @Test
        public void 終了時間を確認するメソッドはtrueを返すこと() {
            // setup
            WorkTime workTime = WorkTime.createConcludedWorkTime(DATETIME_1, DATETIME_2);
            
            // exercose
            boolean actual = workTime.hasEndTime();
            
            // verify
            assertThat(actual, is(true));
        }
        
        @Test(expected=IllegalArgumentException.class)
        public void 開始時間が終了時間より後の場合_例外がスローされること() {
            // exercise
            WorkTime.createConcludedWorkTime(DATETIME_2, DATETIME_1);
        }

        @Test(expected=IllegalArgumentException.class)
        public void 開始時間と終了時間が同じ場合_例外がスローされること() {
            // exercise
            WorkTime.createConcludedWorkTime(DATETIME_1, DATETIME_1);
        }
        
        @Test
        public void 開始時間と終了時間の差分がミリ秒で取得できること() {
            // setup
            WorkTime workTime = WorkTime.createConcludedWorkTime(DATETIME_1, DATETIME_4);
            
            // exercise
            long duration = workTime.getDuration();
            
            // verify
            assertThat(duration, is(300L));
        }
        
        @Test
        public void 作業時間が重なっている場合_isDuplicateがtrueを返すこと() {
            // setup
            WorkTime time1 = WorkTime.createConcludedWorkTime(DATETIME_1, DATETIME_2);
            WorkTime time2 = WorkTime.createConcludedWorkTime(DATETIME_2, DATETIME_3);
            
            // exercise
            boolean duplicate = time1.isDuplicate(time2);
            
            // verify
            assertThat(duplicate, is(true));
        }
        
        @Test
        public void 作業時間が重なっている場合_isDuplicateがtrueを返すこと_開始時間のみを設定している作業時間と比較した場合() {
            // setup
            WorkTime time1 = WorkTime.createConcludedWorkTime(DATETIME_1, DATETIME_2);
            WorkTime time2 = WorkTime.createInWorkingTime(DATETIME_2);
            
            // exercise
            boolean duplicate = time1.isDuplicate(time2);
            
            // verify
            assertThat(duplicate, is(true));
        }
        
        @Test
        public void 作業時間が重なっていない場合_isDuplicateがfalseを返すこと() {
            // setup
            WorkTime time1 = WorkTime.createConcludedWorkTime(DATETIME_1, DATETIME_2);
            WorkTime time2 = WorkTime.createConcludedWorkTime(DATETIME_3, DATETIME_4);
            
            // exercise
            boolean duplicate = time1.isDuplicate(time2);
            
            // verify
            assertThat(duplicate, is(false));
        }
        
        @Test
        public void 作業時間が重なっていない場合_isDuplicateがfalseを返すこと_開始時間のみを設定している作業時間と比較した場合() {
            // setup
            WorkTime time1 = WorkTime.createConcludedWorkTime(DATETIME_1, DATETIME_2);
            WorkTime time2 = WorkTime.createInWorkingTime(DATETIME_3);
            
            // exercise
            boolean duplicate = time1.isDuplicate(time2);
            
            // verify
            assertThat(duplicate, is(false));
        }

        @Test
        public void 開始時間と終了時間が同じ場合_equalsメソッドはtrueを返すこと() {
            // setup
            WorkTime one = WorkTime.createConcludedWorkTime(DATETIME_1, DATETIME_2);
            WorkTime other = WorkTime.createConcludedWorkTime(DATETIME_1, DATETIME_2);
            
            // verify
            assertThat(one.equals(other), is(true));
        }
        
        @Test
        public void 終了時間のコピーが取得できること() {
            // setup
            WorkTime time = WorkTime.createConcludedWorkTime(DATETIME_1, DATETIME_2);
            
            // exercise
            Date endTime = time.getEndTime();
            
            // verify
            assertThat(endTime, is(not(sameInstance(DATETIME_2))));
            assertThat(endTime, is(DATETIME_2));
        }
    }
}
