package test.matcher;

import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import rusk.domain.task.Task;

/**
 * {@link Task} の内容を比較検証する Matcher クラス。
 */
public class TaskMatcher extends TypeSafeMatcher<Task> {
    
    private final Task expected;
    private Without withoutMatchers;
    private String message;
    
    TaskMatcher(Task expected, Without withoutMatchers) {
        this.expected = expected;
        this.withoutMatchers = withoutMatchers;
    }

    @Override
    protected boolean matchesSafely(Task actual) {
        List<TaskPropertyMatcher> matchers = this.withoutMatchers.apply(TaskPropertyMatcher.values());

        try {
            for (TaskPropertyMatcher matcher : matchers) {
                matcher.verify(actual, this.expected);
            }
            return true;
        } catch (NotMatchException e) {
            this.message = e.getMessage();
            return false;
        }
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(this.message);
    }
}
