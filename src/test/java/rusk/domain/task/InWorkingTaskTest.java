package rusk.domain.task;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static test.matcher.RuskMatchers.*;

import java.util.Date;
import java.util.List;

import mockit.NonStrictExpectations;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import rusk.common.util.DateUtil;
import rusk.common.util.Now;

@RunWith(Enclosed.class)
public class InWorkingTaskTest {
    
    private static final Date DATETIME_1 = DateUtil.create("2014-01-01 15:00:00");
    private static final Date DATETIME_2 = DateUtil.addMilliseconds(DATETIME_1, 100);
    private static final Date DATETIME_3 = DateUtil.addMilliseconds(DATETIME_2, 100);
    private static final Date DATETIME_4 = DateUtil.addMilliseconds(DATETIME_3, 100);

    private static final Date registeredDate = DATETIME_1;
    private static final Date startTime = DATETIME_2;
    private static final Date forUrgency = DATETIME_3;
    private static final Date endTime = DATETIME_3;
    private static final Date completedTime = DATETIME_4;
    private static final Date period = DATETIME_4;
    
    public static class インスタンス生成時のテスト {
        
        @Test
        public void 作業中の作業時間が生成されること() {
            // setup
            new NonStrictExpectations(Now.class) {{
                Now.getForStartTime(); result = DATETIME_2;
            }};
            
            UnstartedTask baseTask = new UnstartedTask(DATETIME_1);
            
            // exercise
            InWorkingTask task = InWorkingTask.switchFrom(baseTask);
            
            // verify
            assertThat(task.getWorkTimeInWorking(), is(startAndEndTimeOf(DATETIME_2, null)));
        }
    }
    
    public static class 中断タスクに変換した場合 {

        private InWorkingTask inWorkingTask;
        private Task stoppedTask;
        
        @Before
        public void setup() {
            // setup
            new NonStrictExpectations(Now.class) {{
                Now.getForUrgency(); result = forUrgency;
                Now.getForEndTime(); result = endTime;
            }};
            
            inWorkingTask = createInWorkingTask();
            
            // exercise
            stoppedTask = inWorkingTask.switchToStoppedTask();
        }
        
        @Test
        public void 現在時刻が_作業中だった作業時間の終了時間に設定されていること() {
            // verify
            List<WorkTime> workTimes = stoppedTask.getWorkTimes();
            assertThat(workTimes.size(), is(1));
            
            assertThat(workTimes.get(0), is(startAndEndTimeOf(startTime, endTime)));
        }
        
        @Test
        public void 作業時間以外は_作業中のときと同じ値が設定されていること() {
            // verify
            assertThat(stoppedTask, is(sameTaskWithoutWorkTime(inWorkingTask)));
        }
    }
    
    public static class 完了タスクに変換した場合 {
        
        private InWorkingTask inWorkingTask;
        private Task completedTask;
        
        @Before
        public void setup() {
            // setup
            new NonStrictExpectations(Now.class) {{
                Now.getForUrgency(); result = forUrgency;
                Now.getForCompletedDate(); result = completedTime;
                Now.getForEndTime(); result = endTime;
            }};

            inWorkingTask = createInWorkingTask();

            // exercise
            completedTask = inWorkingTask.switchToCompletedTask();
        }
        
        @Test
        public void 現在時刻が_作業中だった作業時間の終了時間に設定されていること() {
            // verify
            List<WorkTime> workTimes = completedTask.getWorkTimes();
            assertThat(workTimes.size(), is(1));
            
            assertThat(workTimes.get(0), is(startAndEndTimeOf(startTime, endTime)));
        }
        
        @Test
        public void 作業時間と完了時間以外は_作業中のときと同じ値が設定されていること() {
            // verify
            assertThat(completedTask, is(sameTaskWithoutWorkTimeAndCompletedTime(inWorkingTask)));
        }
    }
    
    private static InWorkingTask createInWorkingTask() {
        InWorkingTask inWorkingTask = InWorkingTask.build(1L, registeredDate);
        inWorkingTask.setTitle("title");
        inWorkingTask.setDetail("detail");
        inWorkingTask.setPriority(Priority.of(period, Importance.C));
        inWorkingTask.addWorkTime(WorkTime.createInWorkingTime(startTime));
        
        return inWorkingTask;
    }
    
    public static class 作業時間取得メソッドのテスト {

        @Test
        public void 作業時間を取得すると_現在のこのタスクの作業開始時間も一緒に取得できること() {
            // setup
            InWorkingTask inWorkingTask = createInWorkingTask();
            
            // exercise
            List<WorkTime> workTimes = inWorkingTask.getWorkTimes();
            
            // verify
            WorkTime inWorkingTime = workTimes.stream().filter(time -> !time.hasEndTime()).findFirst().get();
            assertThat(inWorkingTime.getStartTime(), is(startTime));
        }
    }
}
