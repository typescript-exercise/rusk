package rusk.test.matcher;

import static org.hamcrest.Matchers.*;

import java.util.Date;

import org.hamcrest.Matcher;

import rusk.domain.task.Task;
import rusk.domain.task.WorkTime;

public final class RuskMatchers {
    
    public static TaskMatcher sameTaskOf(Task expected) {
        return new TaskMatcher(expected);
    }
    
    public static TaskMatcherWithoutWorkTimeAndCompletedTime sameTaskWithoutWorkTimeAndCompletedTime(Task expected) {
        return new TaskMatcherWithoutWorkTimeAndCompletedTime(expected);
    }
    
    public static TaskMatcherWithoutWorkTime sameTaskWithoutWorkTime(Task expected) {
        return new TaskMatcherWithoutWorkTime(expected);
    }
    
    public static Matcher<WorkTime> startAndEndTimeOf(Date startTime, Date endTime) {
        if (endTime == null) {
            return is(WorkTime.createInWorkingTime(startTime));
        } else {
            return is(WorkTime.createConcludedWorkTime(startTime, endTime));
        }
    }
    
    private RuskMatchers() {}
}
