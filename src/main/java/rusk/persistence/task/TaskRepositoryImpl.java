package rusk.persistence.task;

import static java.util.stream.Collectors.*;
import static rusk.util.LamdaUtil.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import net.sf.persist.Persist;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rusk.domain.task.Task;
import rusk.domain.task.TaskFactory;
import rusk.domain.task.TaskNotFoundException;
import rusk.domain.task.TaskRepository;
import rusk.domain.task.WorkTime;
import rusk.system.db.PersistProvider;
import rusk.util.DateUtil;

/**
 * {@link TaskRepository} の実装クラス。
 * <p>
 * データベースに保存されたタスクとやり取りをする。
 */
public class TaskRepositoryImpl implements TaskRepository {
    private static final Logger logger = LoggerFactory.getLogger(TaskRepositoryImpl.class);
    
    private final PersistProvider provider;
    
    /**
     * コンストラクタ。
     * @param provider {@link PersistProvider}
     * @param workTimeRepository {@link WorkTimeRepository}
     */
    @Inject
    public TaskRepositoryImpl(PersistProvider provider) {
        this.provider = provider;
    }

    @Override
    public boolean existsTaskInWorking() {
        long count = this.provider.getPersist().read(Long.class, "SELECT COUNT(*) FROM TASK WHERE STATUS=1");
        return count != 0;
    }

    @Override
    public Task inquireTaskInWork() {
        TaskTable table = this.readTaskTable("SELECT * FROM TASK WHERE STATUS=1");
        return table == null ? null : this.toTask(table);
    }
    
    private TaskTable readTaskTable(String sql, Object... parameters) {
        return this.provider.getPersist().read(TaskTable.class, sql, parameters);
    }

    @Override
    public List<Task> inquireUncompletedTasks() {
        return this.readList("SELECT * FROM TASK WHERE STATUS IN (0, 2)");
    }

    @Override
    public List<Task> inquireCompleteTasks(final Date date) {
        Date startOfDate = DateUtil.truncate(date, Calendar.DATE);
        Date startOfNextDate = DateUtil.addDays(startOfDate, 1);
        
        return this.readList("SELECT * FROM TASK WHERE STATUS = 3 AND ? <= COMPLETED_DATE AND COMPLETED_DATE < ?", startOfDate, startOfNextDate);
    }
    
    private List<Task> readList(String sql, Object... parameters) {
        List<TaskTable> recordList = this.provider.getPersist().readList(TaskTable.class, sql, parameters);
        return this.mapTaskTableListToTaskList(recordList);
    }
    
    private List<Task> mapTaskTableListToTaskList(List<TaskTable> from) {
        return from.stream().map(this::toTask).collect(toList());
    }
    
    @Override
    public Task inquireById(long id) throws TaskNotFoundException {
        TaskTable taskTable = this.provider.getPersist().readByPrimaryKey(TaskTable.class, id);
        if (taskTable == null) {
            throw new TaskNotFoundException(id);
        }
        
        return this.toTask(taskTable);
    }
    
    private Task toTask(TaskTable table) {
        List<WorkTime> workTimes = this.findWorkTimeList(table.getId());
        
        TaskFactory builder = TaskFactory.withBuilder(table.id, table.registeredDate, table.completedDate, table.getStatusAsEnum());
        
        return builder.title(table.title)
                    .detail(table.detail)
                    .priority(table.period, table.getImportanceAsEnum())
                    .workTimes(workTimes)
                    .build();
    }
    
    private List<WorkTime> findWorkTimeList(long taskId) {
        List<WorkTimeTable> workTimeRecords = this.readWorkTimeTableList("SELECT * FROM WORK_TIME WHERE TASK_ID=? ORDER BY START_TIME ASC", taskId);
        return this.mapToWorkTimeList(workTimeRecords);
    }
    
    private List<WorkTimeTable> readWorkTimeTableList(String sql, Object... parameters) {
        return this.provider.getPersist().readList(WorkTimeTable.class, sql, parameters);
    }
    
    private List<WorkTime> mapToWorkTimeList(List<WorkTimeTable> table) {
        return list(table).map(this::toWorkTime);
    }
    
    private WorkTime toWorkTime(WorkTimeTable table) {
        if (table.hasEndTime()) {
            return WorkTime.deserializeConcludedWorkTime(table.id, table.startTime, table.endTime);
        } else {
            return WorkTime.deserializeInWorkingTime(table.id, table.startTime);
        }
    }

    @Override
    public void register(Task task) {
        Validate.notNull(task, "タスクが null です。");
        
        logger.debug("registered task = {}", task);
        
        TaskTable newRecord = new TaskTable(task);
        this.provider.getPersist().insert(newRecord);
        
        task.setId(newRecord.getId());
    }

    @Override
    public Task inquireWithLock(long id) throws TaskNotFoundException {
        TaskTable taskTable = this.readTaskTable("SELECT * FROM TASK WHERE ID=? FOR UPDATE", id);
        if (taskTable == null) {
            throw new TaskNotFoundException(id);
        }
        
        return this.toTask(taskTable);
    }

    @Override
    public void remove(long deleteTargetTaskId) {
        this.inquireWithLock(deleteTargetTaskId);
        this.removeWorkTimeByTaskId(deleteTargetTaskId);
        this.removeTask(deleteTargetTaskId);
    }
    
    private void removeWorkTimeByTaskId(long taskId) {
        this.executeUpdate("DELETE FROM WORK_TIME WHERE TASK_ID=?", taskId);
    }
    
    private void removeTask(long id) {
        this.executeUpdate("DELETE FROM TASK WHERE ID=?", id);
    }
    
    private void executeUpdate(String sql, Object... parameters) {
        Persist persist = this.provider.getPersist();
        persist.executeUpdate(sql, parameters);
    }

    @Override
    public void saveModification(Task task) {
        this.updateTask(task);
        this.updateWorkTimes(task);
        this.insertWorkTimes(task);
    }

    private void updateTask(Task task) {
        TaskTable taskTable = new TaskTable(task);
        this.provider.getPersist().update(taskTable);
    }

    private void updateWorkTimes(Task task) {
        List<WorkTimeTable> workTimeTables = WorkTimeTable.createBy(task);
        
        List<WorkTimeTable> workTimeTablesForBatchUpdate = list(workTimeTables).filter(this::existsWorkTime);
        
        this.provider.getPersist().updateBatch(workTimeTablesForBatchUpdate.toArray());
    }
    
    private void insertWorkTimes(Task task) {
        List<WorkTimeTable> workTimeTables = WorkTimeTable.createBy(task);
        
        List<WorkTimeTable> workTimeTablesForBatchInsert = list(workTimeTables).negateFilter(this::existsWorkTime);
        
        this.provider.getPersist().insertBatch(workTimeTablesForBatchInsert.toArray());
    }
    
    private boolean existsWorkTime(WorkTimeTable workTimeTable) {
        long count = this.provider.getPersist().read(Long.class, "SELECT COUNT(*) FROM WORK_TIME WHERE ID=?", workTimeTable.getId());
        return count != 0;
    }
}
