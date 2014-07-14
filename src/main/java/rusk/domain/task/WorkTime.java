package rusk.domain.task;

import java.util.Date;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.time.DurationFormatUtils;

/**
 * 作業時間内訳
 */
public class WorkTime {
    
    private final long startTime;
    private final long endTime;
    
    /**
     * コンストラクタ。
     * 
     * @param startTime 開始時間
     * @param endTime 終了時間
     * @throws IllegalArgumentException 終了時間 &lt;= 開始時間 の場合
     */
    public WorkTime(Date startTime, Date endTime) {
        this.validate(startTime, endTime);
        
        this.startTime = startTime.getTime();
        this.endTime = endTime.getTime();
    }
    
    private void validate(Date startTime, Date endTime) {
        Validate.notNull(startTime, "開始時間は必須です。");
        Validate.notNull(endTime, "終了時間は必須です。");
        
        if (endTime.getTime() <= startTime.getTime()) {
            throw new IllegalArgumentException("開始時間は終了時間より前である必要があります。開始時間=" + startTime + ", 終了時間=" + endTime);
        }
    }
    
    /**
     * 開始時間のコピーを取得する。
     * @return 開始時間のコピー
     */
    public Date getStartTime() {
        return new Date(this.startTime);
    }
    /**
     * 終了時間のコピーを取得する。
     * @return 終了時間のコピー
     */
    public Date getEndTime() {
        return new Date(this.endTime);
    }
    
    @Override
    public String toString() {
        return DurationFormatUtils.formatDuration(this.getDuration(), "HH:mm:ss.SSS");
    }

    /**
     * この作業時間をミリ秒で取得する。
     * 
     * @return 作業時間（ミリ秒）
     */
    public long getDuration() {
        return this.endTime - this.startTime;
    }

    /**
     * この作業時間と、指定した作業時間が重複するかどうかを確認します。
     * @param other 重複するか確認するもう１つの作業時間
     * @return 作業時間が重複する場合は true
     */
    public boolean isDuplicate(WorkTime other) {
        return this.isInclude(other.startTime) || this.isInclude(other.endTime);
    }
    
    private boolean isInclude(long time) {
        return this.startTime <= time && time <= this.endTime;
    }
}
