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
    
    private String differentValueName;
    private Object expectedValue;
    
    TaskMatcher(Task expected) {
        this.expected = expected;
    }

    @Override
    protected boolean matchesSafely(Task actual) {
        if (!Objects.equals(actual.getId(), this.expected.getId())) {
            this.setDescribe("ID", this.expected.getId());
            return false;
        } else if (!Objects.equals(actual.getTitle(), this.expected.getTitle())) {
            this.setDescribe("Title", this.expected.getTitle());
            return false;
        } else if (!Objects.equals(actual.getDetail(),  this.expected.getDetail())) {
            this.setDescribe("Detail", this.expected.getDetail());
            return false;
        } else if (!Objects.equals(actual.getRegisteredDate(), this.expected.getRegisteredDate())) {
            this.setDescribe("RegisteredDate", this.expected.getRegisteredDate());
            return false;
        } else if (!Objects.equals(actual.getPriority(), this.expected.getPriority())) {
            this.setDescribe("Priority", this.expected.getPriority());
            return false;
        } else if (!Objects.equals(actual.getWorkTimes(), this.expected.getWorkTimes())) {
            this.setDescribe("WorkTimes", this.expected.getWorkTimes());
            return false;
        }
        
        return true;
    }
    
    private void setDescribe(String differentValueName, Object expectedValue) {
        this.differentValueName = differentValueName;
        this.expectedValue = expectedValue;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(this.differentValueName + " = " + this.expectedValue);
    }
}
