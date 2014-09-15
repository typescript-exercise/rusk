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
    
    private long id;
    private long startTime;
    private Long endTime; // 終了日時は null 可のため、型を Long にしている
    private long updateDate;
    
    /**
     * 指定した作業時間と同じフィールドを持つコピーを作成する。
     * 
     * @param original コピー元の作業時間
     */
    WorkTime(WorkTime original) {
        this.id = original.id;
        this.startTime = original.startTime;
        this.endTime = original.endTime;
        this.updateDate = original.updateDate;
    }
    
    /**
     * 新規に作業中の作業時間を作成します。
     * 
     * @param startTime 開始時間
     * @return 作業中の作業時間。
     */
    public static WorkTime createInWorkingTime(Date startTime) {
        return new WorkTime(startTime);
    }
    
    private WorkTime(Date startTime) {
        this.setStartTime(startTime);
    }
    
    void setStartTime(Date startTime) {
        Validate.notNull(startTime, "開始時間は必須です。");
        
        this.startTime = startTime.getTime();
    }
    
    /**
     * 永続化されていた作業中の作業時間を再構築します。
     * 
     * @param id ID
     * @param startTime 開始時間
     * @return 再構築された作業中の作業時間
     */
    public static WorkTime deserializeInWorkingTime(long id, Date startTime, Date updateDate) {
        return new WorkTime(id, startTime, updateDate);
    }

    private WorkTime(long id, Date startTime, Date updateDate) {
        this.id = id;
        this.setStartTime(startTime);
        this.setUpdateDate(updateDate);
    }
    
    /**
     * 新規に完了した作業時間を作成します。
     * 
     * @param startTime 開始時間
     * @param endTime 終了時間
     * @return 完了した作業時間
     */
    public static WorkTime createConcludedWorkTime(Date startTime, Date endTime) {
        return new WorkTime(startTime, endTime);
    }
    
    private WorkTime(Date startTime, Date endTime) {
        this.setStartTime(startTime);
        this.setEndTime(endTime);
    }
    
    void setEndTime(Date endTime) {
        if (endTime == null) {
            this.endTime = null;
        } else {
            this.validateStartEndTimeRelation(this.startTime, endTime.getTime());
            this.endTime = endTime.getTime();
        }
    }
    
    private void validateStartEndTimeRelation(long startTime, long endTime) {
        if (endTime <= startTime) {
            throw new IllegalArgumentException("開始時間は終了時間より前である必要があります。開始時間=" + startTime + ", 終了時間=" + endTime);
        }
    }
    
    /**
     * 永続化されていた完了した作業時間を再構築します。
     * 
     * @param id ID
     * @param startTime 開始時間
     * @param endTime 終了時間
     * @return 再構築された完了した作業時間
     */
    public static WorkTime deserializeConcludedWorkTime(long id, Date startTime, Date endTime, Date updateDate) {
        return new WorkTime(id, startTime, endTime, updateDate);
    }
    
    private WorkTime(long id, Date startTime, Date endTime, Date updateDate) {
        this.id = id;
        this.setStartTime(startTime);
        this.setEndTime(endTime);
        this.setUpdateDate(updateDate);
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
     * この作業時間に、終了時間が設定されているかどうかを確認します。
     * 
     * @return 終了時間が設定されている場合は true
     */
    public boolean hasEndTime() {
        return this.endTime != null;
    }
    
    public long getId() {
        return id;
    }

    public Date getUpdateDate() {
        return new Date(this.updateDate);
    }

    void setUpdateDate(Date updateDate) {
        Validate.notNull(updateDate, "更新日時に null は設定できません");
        this.updateDate = updateDate.getTime();
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
    WorkTime() {
        this.startTime = Long.MIN_VALUE;
        this.endTime = Long.MIN_VALUE;
    }
}
