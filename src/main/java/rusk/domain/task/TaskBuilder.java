package rusk.domain.task;

import java.util.Date;
import java.util.List;

import rusk.util.DateUtil;
import rusk.util.Today;

/**
 * {@link Task} のインスタンスを簡潔な記述で生成できるようにするためのビルダークラス。
 * <p>
 * 日付項目で文字列を渡すメソッドは、内部で {@link DateUtil#create(String)} を使用しているので、
 * それに合わせた日付フォーマットで渡してください。
 */
public class TaskBuilder {
    
    private final Task task;
    
    public TaskBuilder(long id, String registeredDate) {
        this.task = new Task(id, DateUtil.create(registeredDate));
    }
    
    public TaskBuilder(long id, Date registeredDate) {
        this.task = new Task(id, registeredDate);
    }
    
    public TaskBuilder title(String title) {
        this.task.setTitle(title);
        return this;
    }
    
    public TaskBuilder status(Status status) {
        this.task.setStatus(status);
        return this;
    }
    
    public TaskBuilder detail(String detail) {
        this.task.setDetail(detail);
        return this;
    }
    
    public TaskBuilder completeDate(String completedDate) {
        if (completedDate != null) {
            this.task.setCompletedDate(DateUtil.create(completedDate));
        }
        return this;
    }
    
    public TaskBuilder completeDate(Date completedDate) {
        this.task.setCompletedDate(completedDate);
        return this;
    }
    
    public TaskBuilder priority(String period, Importance importance) {
        Urgency urgency = new Urgency(Today.get(), DateUtil.create(period));
        this.task.setPriority(new Priority(urgency, importance));
        return this;
    }
    
    public TaskBuilder priority(Date period, Importance importance) {
        Urgency urgency = new Urgency(Today.get(), period);
        this.task.setPriority(new Priority(urgency, importance));
        return this;
    }
    
    public TaskBuilder addWorkTime(String from, String to) {
        WorkTime workTime = new WorkTime(DateUtil.create(from), DateUtil.create(to));
        this.task.addWorkTime(workTime);
        return this;
    }
    
    public TaskBuilder workTimes(List<WorkTime> workTimes) {
        this.task.setWorkTimes(workTimes);
        return this;
    }
    
    public Task build() {
        return this.task;
    }
}
