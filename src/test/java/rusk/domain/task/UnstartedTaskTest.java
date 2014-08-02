package rusk.domain.task;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static rusk.test.matcher.RuskMatchers.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import rusk.util.DateUtil;

@RunWith(Enclosed.class)
public class UnstartedTaskTest {

    private static final Date DATETIME_1 = DateUtil.create("2014-01-01 15:00:00");
    private static final Date DATETIME_2 = DateUtil.addMilliseconds(DATETIME_1, 100);
    private static final Date DATETIME_3 = DateUtil.addMilliseconds(DATETIME_2, 100);
    private static final Date DATETIME_4 = DateUtil.addMilliseconds(DATETIME_3, 100);

    public static class 作業中タスクに変換した場合 {
        
        private UnstartedTask unstartedTask;
        private Task inWorkingTask;
        
        @Before
        public void setup() {
            // setup
            unstartedTask = createUnstartedTask();
            
            // exercise
            inWorkingTask = unstartedTask.switchToInWorkingTask();
        }
        
        @Test
        public void 作業中タスクが生成されること() {
            // verify
            assertThat(inWorkingTask, is(instanceOf(InWorkingTask.class)));
        }
        
        @Test
        public void 作業時間以外は_未着手のときと同じ値が設定されていること() {
            // verify
            assertThat(inWorkingTask, is(sameTaskWithoutWorkTime(unstartedTask)));
        }
    }
    
    public static class 完了タスクに変換した場合 {
        
        private UnstartedTask unstartedTask;
        private Task completedTask;
        
        @Before
        public void setup() {
            // setup
            unstartedTask = createUnstartedTask();
            
            // exercise
            completedTask = unstartedTask.switchToCompletedTask();
        }
        
        @Test
        public void 完了タスクが生成されること() {
            // verify
            assertThat(completedTask, is(instanceOf(CompletedTask.class)));
        }
        
        @Test
        public void 未着手のときと同じ値が設定されていること() {
            // verify
            assertThat(completedTask, is(sameTaskOf(unstartedTask)));
        }
    }
    
    private static UnstartedTask createUnstartedTask() {
        UnstartedTask unstartedTask = new UnstartedTask(1L, DATETIME_1);
        unstartedTask.setTitle("title");
        unstartedTask.setDetail("detail");
        unstartedTask.setPriority(Priority.of(DATETIME_4, Importance.B));
        
        return unstartedTask;
    }
}
