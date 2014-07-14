package rusk.persistence.task;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import rusk.domain.task.WorkTime;
import rusk.domain.task.WorkTimeRepository;
import rusk.system.db.PersistProvider;

/**
 * {@link WorkTimeRepository} の実装クラス。
 * <p>
 * データベースに保存されたタスクとやり取りをする。
 */
public class WorkTimeRepositoryImpl implements WorkTimeRepository {
    
    private final PersistProvider provider;
    
    @Inject
    public WorkTimeRepositoryImpl(PersistProvider provider) {
        this.provider = provider;
    }

    @Override
    public List<WorkTime> findByTaskId(long taskId) {
        List<WorkTimeTable> tables = this.provider.getPersist()
                                            .readList(WorkTimeTable.class, "SELECT * FROM WORK_TIME WHERE TASK_ID=? ORDER BY START_TIME ASC", taskId);
        
        return tables.stream().map(this::map).collect(Collectors.toList());
    }
    
    private WorkTime map(WorkTimeTable table) {
        return new WorkTime(table.getStartTime(), table.getEndTime());
    }
}
