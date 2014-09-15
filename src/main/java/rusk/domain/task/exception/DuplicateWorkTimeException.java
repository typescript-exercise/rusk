package rusk.domain.task.exception;

import org.apache.commons.lang3.time.FastDateFormat;

import rusk.domain.task.WorkTime;

/**
 * タスクに登録された作業時間が重複していることを表す例外。
 */
public class DuplicateWorkTimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public DuplicateWorkTimeException(WorkTime duplicate) {
        super("work time is duplicated. duplicate = " + format(duplicate));
    }
    
    public DuplicateWorkTimeException(WorkTime already, WorkTime duplicate) {
        super("work time is duplicated. already = " + format(already) + ", duplicate = " + format(duplicate));
    }
    
    private static String format(WorkTime time) {
        FastDateFormat formatter = FastDateFormat.getInstance("yyyy/MM/dd HH:mm:ss");
        if (time.hasEndTime()) {
            return "[" + formatter.format(time.getStartTime()) + " - " + formatter.format(time.getEndTime()) + "]";
        } else {
            return "[" + formatter.format(time.getStartTime()) + "]";
        }
    }
}
