package rusk.persistence.task;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rusk.domain.task.Priority;
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
    private static final Logger logger = LoggerFactory.getLogger(TaskRepositoryImpl.class);
    
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
        return table == null ? null : this.toTask(table);
    }

    @Override
    public List<Task> findUncompletedTasks() {
        List<TaskTable> tables = this.provider.getPersist().readList(TaskTable.class, "SELECT * FROM TASK WHERE STATUS IN (0, 2)");
        return tables.stream().map(this::toTask).collect(Collectors.toList());
    }

    @Override
    public List<Task> findCompleteTasks(Date date) {
        Date from = DateUtil.truncate(date, Calendar.DATE);
        Date to = DateUtil.addDays(from, 1);
        
        List<TaskTable> tables = this.provider.getPersist()
                                        .readList(TaskTable.class,
                                                "SELECT * FROM TASK WHERE STATUS = 3 AND ? <= COMPLETED_DATE AND COMPLETED_DATE < ?",
                                                from, to);
        
        return tables.stream().map(this::toTask).collect(Collectors.toList());
    }

    public Task findById(long id) {
        TaskTable taskTable = this.provider.getPersist().readByPrimaryKey(TaskTable.class, id);
        return this.toTask(taskTable);
    }
    
    private Task toTask(TaskTable table) {
        Task task = new Task(table.getId(), table.getRegisteredDate());
        task.setTitle(table.getTitle());
        task.setStatus(table.getStatusAsEnum());
        task.setDetail(table.getDetail());
        task.setCompletedDate(table.getCompletedDate());
        
        Urgency urgency = new Urgency(Today.get(), table.getPeriod());
        Priority priority = new Priority(urgency, table.getImportanceAsEnum());
        task.setPriority(priority);
        
        List<WorkTime> workTimes = this.workTimeRepository.findByTaskId(task.getId());
        task.setWorkTimes(workTimes);
        
        return task;
    }

    @Override
    public long register(Task task) {
        Validate.notNull(task, "タスクが null です。");
        
        logger.debug("registered task = {}", task);
        
        TaskTable table = new TaskTable(task);
        this.provider.getPersist().insert(table);
        return table.getId();
    }
}
