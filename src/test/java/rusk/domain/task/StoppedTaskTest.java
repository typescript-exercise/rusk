package rusk.domain.task;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static rusk.test.matcher.RuskMatchers.*;

import java.util.Date;

import mockit.NonStrictExpectations;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import rusk.common.util.DateUtil;
import rusk.common.util.Now;

@RunWith(Enclosed.class)
public class StoppedTaskTest {

    private static final Date DATETIME_1 = DateUtil.create("2014-01-01 15:00:00");
    private static final Date DATETIME_2 = DateUtil.addMilliseconds(DATETIME_1, 100);
    private static final Date DATETIME_3 = DateUtil.addMilliseconds(DATETIME_2, 100);
    private static final Date DATETIME_4 = DateUtil.addMilliseconds(DATETIME_3, 100);
    
    private static final Date registeredDate = DATETIME_1;
    private static final Date forUrgency = DATETIME_2;
    private static final Date period = DATETIME_4;
    private static final Date startTime = DATETIME_3;
    
    public static class 作業中に変換した場合 {
        
        private StoppedTask stoppedTask;
        private Task inWorkingTask;

        @Before
        public void setup() {
            // setup
            new NonStrictExpectations(Now.class) {{
                Now.getForUrgency(); result = forUrgency;
                Now.getForStartTime(); result = startTime;
            }};
            
            stoppedTask = createStoppedTask();
            
            // exercise
            inWorkingTask = stoppedTask.switchToInWorkingTask();
        }
        
        @Test
        public void 作業中タスクが生成されること() {
            // verify
            assertThat(inWorkingTask, is(instanceOf(InWorkingTask.class)));
        }
        
        @Test
        public void 作業時間以外は_中断時と同じ値が設定されていること() {
            // verify
            assertThat(inWorkingTask, is(sameTaskWithoutWorkTime(stoppedTask)));
        }
    }
    
    public static class 完了に変換した場合 {
        
        private StoppedTask stoppedTask;
        private Task completedTask;
        
        @Before
        public void setup() {
            // setup
            new NonStrictExpectations(Now.class) {{
                Now.getForUrgency(); result = forUrgency;
                Now.getForStartTime(); result = startTime;
            }};
            
            stoppedTask = createStoppedTask();
            
            // exercise
            completedTask = stoppedTask.switchToCompletedTask();
        }
        
        @Test
        public void 完了タスクが生成されること() {
            // verify
            assertThat(completedTask, is(instanceOf(CompletedTask.class)));
        }
        
        @Test
        public void 中断時と同じ値が設定されていること() {
            // verify
            assertThat(completedTask, is(sameTaskOf(stoppedTask)));
        }
    }
    
    private static StoppedTask createStoppedTask() {
        StoppedTask stoppedTask = new StoppedTask(1L, registeredDate);
        stoppedTask.setTitle("title");
        stoppedTask.setDetail("detail");
        stoppedTask.setPriority(Priority.of(period, Importance.S));
        stoppedTask.addWorkTime(WorkTime.createConcludedWorkTime(DATETIME_1, DATETIME_2));
        
        return stoppedTask;
    }
}
