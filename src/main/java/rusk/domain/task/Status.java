package rusk.domain.task;

import java.util.Date;

import rusk.util.Immutable;
import rusk.util.Today;

/**
 * 状態
 */
@Immutable
public enum Status {
    /**未着手*/
    UNSTARTED {
        @Override protected void processWorkTime(Task task, Date now) {/*処理なし*/}
    },
    /**作業中*/
    IN_WORKING {
        @Override
        protected void processWorkTime(Task task, Date now) {
            if (task.getStatus() == Status.COMPLETE) {
                task.setCompletedDate(null);
            }
            
            WorkTime workTime = new WorkTime(now);
            task.addWorkTime(workTime);
        }
    },
    /**中断*/
    STOPPED {
        @Override
        protected void processWorkTime(Task task, Date now) {
            this.closeTimeInWorking(task, now);
        }
    },
    /**完了*/
    COMPLETE {
        @Override
        protected void processWorkTime(Task task, Date now) {
            this.closeTimeInWorking(task, now);
            task.setCompletedDate(now);
        }
    },
    ;

    protected void closeTimeInWorking(Task task, Date now) {
        WorkTime timeInWorking = task.getWorkTimeInWorking();
        timeInWorking.setEndTime(now);
    }

    public boolean isCompleted() {
        return this == COMPLETE;
    }
    
    /**
     * 変更後の状態に合わせて、タスクの作業時間を更新する。
     * 
     * @param task 更新するタスク
     */
    public void updateWorkTime(Task task) {
        final Date now = Today.get();
        this.processWorkTime(task, now);
    }
    
    abstract protected void processWorkTime(Task task, Date now);
}
