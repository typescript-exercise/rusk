package rusk.domain.task;

import java.util.Date;
import java.util.List;

/**
 * {@link Task} を永続化層とやり取りするためのインターフェース。
 */
public interface TaskRepository {
    
    /**
     * 指定したタスクを、新規登録します。
     * 
     * @param task 登録するタスク
     * @return 新規に採番された ID
     * @throws NullPointerException タスクが null の場合
     */
    long register(Task task);
    
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
    
    /**
     * 指定した ID のタスクを取得する。
     * 
     * @param id タスクの ID
     * @return 取得したタスク
     * @throws TaskNotFoundException ID に紐づくタスクが存在しない場合
     */
    Task inquire(long id) throws TaskNotFoundException;
    
    /**
     * 指定した ID のタスクを取得する。
     * <p>
     * このメソッドは、取得したタスクに対して排他のためのロックを取得します。
     * 
     * @param id タスクの ID
     * @return 取得したタスク。
     * @throws TaskNotFoundException ID に紐づくタスクが存在しない場合
     */
    Task inquireWithLock(long id) throws TaskNotFoundException;

    /**
     * 指定した ID のタスクを削除します。
     * 
     * @param deleteTargetTaskId 削除対象のタスク ID
     */
    void remove(long deleteTargetTaskId);
}
