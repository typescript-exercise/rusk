package rusk.domain.task;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static test.matcher.RuskMatchers.*;

import java.util.Date;

import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import rusk.common.util.DateUtil;
import rusk.common.util.Now;

@RunWith(Enclosed.class)
public class CompletedTaskTest {
    
    private static final Date DATETIME_1 = DateUtil.create("2014-01-01 15:00:00");
    private static final Date DATETIME_2 = DateUtil.addMilliseconds(DATETIME_1, 100);
    private static final Date DATETIME_3 = DateUtil.addMilliseconds(DATETIME_2, 100);
    private static final Date DATETIME_4 = DateUtil.addMilliseconds(DATETIME_3, 100);
    
    public static class コピー生成したときのテスト {
        
        @Mocked
        private InWorkingTask baseTask;
        
        @Test
        public void 現在時刻が完了時刻に設定されること() {
            // setup
            new NonStrictExpectations(Now.class) {{
                Now.getForCompletedDate(); result = DATETIME_2;
            }};
            
            // exercise
            CompletedTask task = CompletedTask.switchFrom(baseTask);
            
            // verify
            assertThat(task.getCompletedDate(), is(DATETIME_2));
        }
    }
    
    public static class 作業中に変換した場合 {
        
        private CompletedTask completedTask;
        private Task inWorkingTask;
        
        @Before
        public void setup() {
            // setup
            completedTask = new CompletedTask(1L, DATETIME_1, DATETIME_4);
            
            // exercise
            inWorkingTask = completedTask.switchToInWorkingTask();
        }
        
        @Test
        public void 作業中タスクに変換されること() {
            // verify
            assertThat(inWorkingTask, is(instanceOf(InWorkingTask.class)));
        }
        
        @Test
        public void 完了時間以外は_作業中のときと同じ値が設定されること() {
            // verify
            assertThat(inWorkingTask, is(sameTaskWithoutWorkTimeAndCompletedTime(completedTask)));
        }
    }
}
