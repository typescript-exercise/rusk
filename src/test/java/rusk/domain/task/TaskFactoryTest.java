package rusk.domain.task;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Date;

import mockit.NonStrictExpectations;

import org.junit.Before;
import org.junit.Test;

import rusk.service.task.RegisterTaskForm;
import rusk.util.DateUtil;
import rusk.util.Now;

public class TaskFactoryTest {
    
    private static final Date PERIOD = DateUtil.create("2014-02-01 10:00:00");
    private static final Date NOW = DateUtil.create("2014-02-01 09:00:00");
    
    @Before
    public void setup() {
        new NonStrictExpectations(Now.class) {{
            Now.getForRegisteredDate(); result = NOW;
        }};
    }
    
    @Test
    public void 指定したフォームの情報を元に新しいタスクが生成されること() {
        // setup
        RegisterTaskForm form = new RegisterTaskForm();
        
        form.setTitle("title");
        form.setDetail("detail");
        form.setImportance(Importance.C);
        form.setPeriod(PERIOD);
        
        // exercise
        Task task = TaskFactory.create(form);
        
        // verify
        assertThat(task.getId(), is(nullValue()));
        assertThat(task.getTitle(), is("title"));
        assertThat(task.getDetail(), is("detail"));
        
        Priority expectedPriority = new Priority(new Urgency(NOW, PERIOD), Importance.C);
        assertThat(task.getPriority(), is(expectedPriority));
        
        assertThat(task.getRegisteredDate(), is(NOW));
        assertThat(task, is(instanceOf(UnstartedTask.class)));
        assertThat(task.getWorkTimes(), is(empty()));
    }

}
