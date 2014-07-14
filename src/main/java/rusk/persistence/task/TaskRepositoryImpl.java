package rusk.persistence.task;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import rusk.domain.task.Importance;
import rusk.domain.task.Priority;
import rusk.domain.task.Status;
import rusk.domain.task.Task;
import rusk.domain.task.TaskRepository;
import rusk.domain.task.Urgency;
import rusk.domain.task.WorkTime;
import rusk.domain.task.WorkTimeRepository;
import rusk.system.db.PersistProvider;
import rusk.util.DateUtil;
import rusk.util.Today;

/**
 * {@link TaskRepository} の実装クラス。
 * <p>
 * データベースに保存されたタスクとやり取りをする。
 */
public class TaskRepositoryImpl implements TaskRepository {
    
    private final PersistProvider provider;
    private final WorkTimeRepository workTimeRepository;
    
    /**
     * コンストラクタ。
     * @param provider {@link PersistProvider}
     * @param workTimeRepository {@link WorkTimeRepository}
     */
    @Inject
    public TaskRepositoryImpl(PersistProvider provider, WorkTimeRepository workTimeRepository) {
        this.provider = provider;
        this.workTimeRepository = workTimeRepository;
    }

    @Override
    public Task findTaskInWork() {
        TaskTable table = this.provider.getPersist().read(TaskTable.class, "SELECT * FROM TASK WHERE STATUS=1");
        return table == null ? null : this.map(table);
    }

    @Override
    public List<Task> findUncompletedTasks() {
        List<TaskTable> tables = this.provider.getPersist().readList(TaskTable.class, "SELECT * FROM TASK WHERE STATUS IN (0, 2)");
        return tables.stream().map(this::map).collect(Collectors.toList());
    }

    @Override
    public List<Task> findCompleteTasks(Date date) {
        Date from = DateUtil.truncate(date, Calendar.DATE);
        Date to = DateUtil.addDays(from, 1);
        
        List<TaskTable> tables = this.provider.getPersist()
                                        .readList(TaskTable.class,
                                                "SELECT * FROM TASK WHERE STATUS = 3 AND ? <= COMPLETED_DATE AND COMPLETED_DATE < ?",
                                                from, to);
        
        return tables.stream().map(this::map).collect(Collectors.toList());
    }

    public Task findById(long id) {
        TaskTable taskTable = this.provider.getPersist().readByPrimaryKey(TaskTable.class, id);
        return this.map(taskTable);
    }
    
    private Task map(TaskTable table) {
        Task task = new Task(table.getId(), table.getRegisteredDate());
        task.setTitle(table.getTitle());
        task.setStatus(this.mapStatus(table.getStatus()));
        task.setDetail(table.getDetail());
        task.setCompletedDate(table.getCompletedDate());
        
        Urgency urgency = new Urgency(Today.get(), table.getPeriod());
        Priority priority = new Priority(urgency, this.mapImportance(table.getImportance()));
        task.setPriority(priority);
        
        List<WorkTime> workTimes = this.workTimeRepository.findByTaskId(task.getId());
        task.setWorkTimes(workTimes);
        
        return task;
    }
    
    private Status mapStatus(byte s) {
        switch (s) {
        case 0:
            return Status.UNSTARTED;
        case 1:
            return Status.IN_WORKING;
        case 2:
            return Status.STOPPED;
        case 3:
            return Status.COMPLETE;
        }
        
        throw new IllegalArgumentException("s = " + s);
    }
    
    private Importance mapImportance(byte i) {
        switch (i) {
        case 0:
            return Importance.C;
        case 1:
            return Importance.B;
        case 2:
            return Importance.A;
        case 3:
            return Importance.S;
        }
        
        throw new IllegalArgumentException("i = " + i);
    }

}
