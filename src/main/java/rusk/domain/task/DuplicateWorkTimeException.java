package rusk.domain.task;

/**
 * タスクに登録された作業時間が重複していることを表す例外。
 */
public class DuplicateWorkTimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public DuplicateWorkTimeException(WorkTime already, WorkTime duplicate) {
        super("work time is duplicated. already = " + already + ", duplicate = " + duplicate);
    }
}
