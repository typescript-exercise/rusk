package rusk.domain.task;

import java.util.Date;
import java.util.List;

import rusk.util.DateUtil;

/**
 * {@link Task} のインスタンスを簡潔な記述で生成できるようにするためのビルダークラス。
 * <p>
 * 日付項目で文字列を渡すメソッドは、内部で {@link DateUtil#create(String)} を使用しているので、
 * それに合わせた日付フォーマットで渡してください。
 */
public class TaskBuilder {
    
    private final Task task;

    public static TaskBuilder completedTask(long id, String registeredDate, String completedDate) {
        Task task = new CompletedTask(id, DateUtil.create(registeredDate), DateUtil.create(completedDate));
        return new TaskBuilder(task);
    }
    
    private TaskBuilder(Task task) {
        this.task = task;
    }
    
    public TaskBuilder title(String title) {
        this.task.setTitle(title);
        return this;
    }
    
    public TaskBuilder detail(String detail) {
        this.task.setDetail(detail);
        return this;
    }
    
    public TaskBuilder completeDate(String completedDate) {
        this.task.setCompletedDate(DateUtil.create(completedDate));
        return this;
    }
    
    public TaskBuilder completeDate(Date completedDate) {
        this.task.setCompletedDate(completedDate);
        return this;
    }
    
    public TaskBuilder priority(String period, Importance importance) {
        this.task.setPriority(Priority.of(DateUtil.create(period), importance));
        return this;
    }
    
    public TaskBuilder priority(Date period, Importance importance) {
        this.task.setPriority(Priority.of(period, importance));
        return this;
    }
    
    public TaskBuilder addWorkTime(String from, String to) {
        WorkTime workTime = WorkTime.createConcludedWorkTime(DateUtil.create(from), DateUtil.create(to));
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
