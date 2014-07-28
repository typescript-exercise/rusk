package rusk.domain.task;

import java.util.Date;

@SuppressWarnings("deprecation")
public class NullObjectWorkTime extends WorkTime {
    
    public void setEndTime(Date endTime) {
        // 処理なし
    }
    
    public Date getStartTime() {
        return null;
    }
    
    public Date getEndTime() {
        return null;
    }

    public long getDuration() {
        return 0L;
    }

    public boolean isDuplicate(WorkTime other) {
        return false;
    }

    public boolean hasEndTime() {
        return false;
    }

    @Override
    public String toString() {
        return "this is null object.";
    }
}
