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
     * @throws NullPointerException タスクが null の場合
     */
    void register(Task task);

    /**
     * 状態が作業中のタスクが存在するかどうかを確認する。
     * 
     * @return 作業中のタスクが存在する場合は true を返す。
     */
    boolean existsTaskInWorking();
    
    /**
     * 状態が作業中のタスクを取得する。
     * 
     * @return 作業中のタスク。存在しない場合は null を返す。
     */
    Task inquireTaskInWork();
    
    /**
     * 状態が未完了（未着手・中断）のタスクを全て取得する。
     * 
     * @return 未完了のタスク。存在しない場合は、空のリストを返す。
     */
    List<Task> inquireUncompletedTasks();
    
    /**
     * 指定した日に完了したタスクを取得する。
     * 
     * @param date ここで指定した日に完了したタスクを検索する。
     * @return 指定日に完了したタスク。存在しない場合は、空のリストを返す。
     */
    List<Task> inquireCompleteTasks(Date date);
    
    /**
     * 指定した ID のタスクを取得する。
     * 
     * @param id タスクの ID
     * @return 取得したタスク
     * @throws TaskNotFoundException ID に紐づくタスクが存在しない場合
     */
    Task inquireById(long id) throws TaskNotFoundException;
    
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
    
    /**
     * 指定したタスクオブジェクトが持つ情報で、永続化されている同一のタスク情報を上書きする。
     * 
     * @param task 更新情報を持ったタスク
     * @throws NullPointerException タスクが null の場合
     * @throws TaskNotFoundException 指定したタスクが存在しない場合
     */
    void saveModification(Task task);
}
