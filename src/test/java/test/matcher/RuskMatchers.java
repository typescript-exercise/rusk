package test.matcher;

import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.Date;

import org.hamcrest.Matcher;

import rusk.domain.task.Task;
import rusk.domain.task.WorkTime;

public final class RuskMatchers {
    
    public static Without without(TaskPropertyMatcher... matchers) {
        return new Without(Arrays.asList(matchers));
    }
    
    public static TaskMatcher sameTaskOf(Task expected, Without without) {
        return new TaskMatcher(expected, without);
    }
    
    public static TaskMatcher sameTaskOf(Task expected) {
        return sameTaskOf(expected, Without.EMPTY);
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
