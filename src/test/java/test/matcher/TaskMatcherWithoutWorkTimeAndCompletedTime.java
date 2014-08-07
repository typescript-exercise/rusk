package test.matcher;

import java.util.Objects;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import rusk.domain.task.Task;

public class TaskMatcherWithoutWorkTimeAndCompletedTime extends TypeSafeMatcher<Task> {

    private final Task expected;
    
    TaskMatcherWithoutWorkTimeAndCompletedTime(Task expected) {
        this.expected = expected;
    }

    @Override
    protected boolean matchesSafely(Task actual) {
        return Objects.equals(actual.getId(), this.expected.getId())
                && Objects.equals(actual.getTitle(), this.expected.getTitle())
                && Objects.equals(actual.getDetail(),  this.expected.getDetail())
                && Objects.equals(actual.getRegisteredDate(), this.expected.getRegisteredDate())
                && Objects.equals(actual.getPriority(), this.expected.getPriority());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(this.expected.toString());
    }
}