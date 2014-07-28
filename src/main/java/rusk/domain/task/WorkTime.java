package rusk.domain.task;

import java.util.Date;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.time.DurationFormatUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 作業時間内訳
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class WorkTime {
    
    private long startTime;
    private Long endTime; // 終了日時は null 可のため、型を Long にしている
    
    /**
     * 開始日時だけを指定してインスタンスを生成する。
     * 
     * @param startTime 開始日時
     * @throws NullPointerException 開始日時が null の場合
     */
    public WorkTime(Date startTime) {
        this.setStartTime(startTime);
    }
    
    /**
     * コンストラクタ。
     * 
     * @param startTime 開始時間
     * @param endTime 終了時間
     * @throws IllegalArgumentException 終了時間 &lt;= 開始時間 の場合
     */
    public WorkTime(Date startTime, Date endTime) {
        this.setStartTime(startTime);
        this.setEndTime(endTime);
    }
    
    private void setStartTime(Date startTime) {
        Validate.notNull(startTime, "開始時間は必須です。");
        
        this.startTime = startTime.getTime();
    }
    
    public void setEndTime(Date endTime) {
        Validate.notNull(endTime, "終了時間は必須です。");
        this.validateStartEndTimeRelation(this.getStartTime(), endTime);
        
        this.endTime = endTime.getTime();
    }
    
    private void validateStartEndTimeRelation(Date startTime, Date endTime) {
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
        return this.endTime == null ? null : new Date(this.endTime);
    }

    /**
     * この作業時間をミリ秒で取得する。
     * 
     * @return 作業時間（ミリ秒）
     */
    public long getDuration() {
        return this.endTime == null ? 0L : this.endTime - this.startTime;
    }

    /**
     * この作業時間と、指定した作業時間が重複するかどうかを確認します。
     * @param other 重複するか確認するもう１つの作業時間
     * @return 作業時間が重複する場合は true
     */
    public boolean isDuplicate(WorkTime other) {
        long otherEndTime = other.endTime != null ? other.endTime : Long.MAX_VALUE;
        
        return this.includes(other.startTime) || this.includes(otherEndTime);
    }
    
    private boolean includes(long time) {
        long endTime = ObjectUtils.defaultIfNull(this.endTime, Long.MAX_VALUE);
        
        return this.startTime <= time && time <= endTime;
    }

    public boolean hasEndTime() {
        return this.endTime != null;
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.startTime).append(this.endTime).toHashCode();
    }
    
    @Override
    public boolean equals(Object other) {
        if (other == null || !(other instanceof WorkTime)) return false;
        
        WorkTime o = (WorkTime)other;
        
        return new EqualsBuilder().append(this.startTime, o.startTime).append(this.endTime, o.endTime).isEquals();
    }

    @Override
    public String toString() {
        return DurationFormatUtils.formatDuration(this.getDuration(), "HH:mm:ss.SSS");
    }
    
    /**
     * このコンストラクタは、フレームワークのために宣言されています。
     */
    @Deprecated
    public WorkTime() {
        this.startTime = Long.MIN_VALUE;
        this.endTime = Long.MIN_VALUE;
    }
}
