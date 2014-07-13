package rusk.domain.task;

import java.util.Date;
import java.util.List;

/**
 * {@link Task} を永続化層とやり取りするためのインターフェース。
 */
public interface TaskRepository {
    
    /**
     * 状態が作業中のタスクを取得する。
     * 
     * @return 作業中のタスク。存在しない場合は null を返す。
     */
    Task findTaskInWork();
    
    /**
     * 状態が未完了（未着手・中断）のタスクを全て取得する。
     * 
     * @return 未完了のタスク。存在しない場合は、空のリストを返す。
     */
    List<Task> findUncompletedTasks();
    
    /**
     * 指定した日に完了したタスクを取得する。
     * 
     * @param date ここで指定した日に完了したタスクを検索する。
     * @return 指定日に完了したタスク。存在しない場合は、空のリストを返す。
     */
    List<Task> findCompleteTasks(Date date);
}
