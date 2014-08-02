package rusk.test.matcher;

import java.util.Objects;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import rusk.domain.task.Task;

/**
 * {@link Task} の内容を比較検証する Matcher クラス。
 */
public class TaskMatcher extends TypeSafeMatcher<Task> {
    
    private final Task expected;
    
    TaskMatcher(Task expected) {
        this.expected = expected;
    }

    @Override
    protected boolean matchesSafely(Task actual) {
        return Objects.equals(actual.getId(), this.expected.getId())
                && Objects.equals(actual.getTitle(), this.expected.getTitle())
                && Objects.equals(actual.getDetail(),  this.expected.getDetail())
                && Objects.equals(actual.getRegisteredDate(), this.expected.getRegisteredDate())
                && Objects.equals(actual.getPriority(), this.expected.getPriority())
                && Objects.equals(actual.getWorkTimes(), this.expected.getWorkTimes());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(this.expected.toString());
    }
}
