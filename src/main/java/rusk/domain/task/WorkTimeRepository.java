package rusk.domain.task;

import java.util.List;


/**
 * {@link WorkTime} を永続化層とやり取りするためのインターフェース。
 */
public interface WorkTimeRepository {
    
    /**
     * 指定したタスクに紐づく作業時間を取得します。
     * <p>
     * 取得した作業時間は、開始時間で昇順にソートされます。
     * 
     * @param taskId タスク ID
     * @return 指定したタスクに紐づく作業時間のリスト。該当する作業時間が存在しない場合、空のリストが返される。
     */
    List<WorkTime> findByTaskId(long taskId);
}
