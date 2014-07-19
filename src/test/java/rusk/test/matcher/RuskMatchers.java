package rusk.test.matcher;

import rusk.domain.task.Task;

public final class RuskMatchers {
    
    public static TaskMatcher sameTaskOf(Task expected) {
        return new TaskMatcher(expected);
    }
    
    private RuskMatchers() {}
}
