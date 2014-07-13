package rusk.domain.task;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.Validate;

import rusk.util.DateUtil;

/**
 * 緊急度
 * <p>
 * 緊急度は、下記ルールに従い基準日と期限の関係から判定されます。
 * <table border="1">
 *   <tr>
 *     <th>基準日と期限の関係</th>
 *     <th>判定結果</th>
 *   </tr>
 *   <tr>
 *     <td>期限と基準日の差が３時間より短い</td>
 *     <td>S（今すぐに対応）</td>
 *   </tr>
 *   <tr>
 *     <td>期限と基準日が同じ日で、差が３時間以上</td>
 *     <td>A（今日中に対応）</td>
 *   </tr>
 *   <tr>
 *     <td>期限と基準日が異なり、期限が基準日から１週間後より前</td>
 *     <td>B（今週中に対応）</td>
 *   </tr>
 *   <tr>
 *     <td>期限が基準日の１週間より後</td>
 *     <td>C（ゆくゆくは対応）</td>
 *   </tr>
 * </table>
 * <p>
 * ただし、期限が基準日より前、つまり時間が経過して期限を過ぎたような場合は、
 * 緊急度は不定（{@link Rank#INDEFINITE}）になります。
 * <p>
 * このクラスには、ランクのみを設定した緊急度のインスタンスが存在します（{@link Urgency#RANK_S}）。
 * これらは期限が設定されていないため、 {@link #getPeriod()} を実行すると {@link UnsupportedOperationException} がスローされます。
 */
public class Urgency implements RankComparator {
    private final Date period;
    private final Rank rank;
    
    /**ランクSの緊急度*/
    public static final Urgency RANK_S = new Urgency(Rank.S);
    /**ランクAの緊急度*/
    public static final Urgency RANK_A = new Urgency(Rank.A);
    /**ランクBの緊急度*/
    public static final Urgency RANK_B = new Urgency(Rank.B);
    /**ランクCの緊急度*/
    public static final Urgency RANK_C = new Urgency(Rank.C);
    
    /**
     * このコンストラクタは、フレームワークのために存在します。
     */
    @Deprecated
    public Urgency() {
        this.period = null;
        this.rank = null;
    }
    
    /**
     * 基準日と期限を指定してインスタンスを生成する。
     * 
     * @param base 基準日
     * @param period 期限
     * @throws NullPointerException 引数のいずれかが null の場合
     * @throws IllegalArgumentException 期限が基準日以前の場合
     */
    public Urgency(Date base, Date period) {
        Validate.notNull(base, "基準日に null は指定できません。");
        Validate.notNull(period, "期限に null は指定できません。");
        
        this.period = new Date(period.getTime());
        this.rank = this.isolateRank(base, period);
    }
    
    /**
     * ランクのみを指定してインスタンスを生成する。
     * 
     * @param rank ランク
     */
    private Urgency(Rank rank) {
        this.rank = rank;
        period = null;
    }
    
    private Rank isolateRank(Date base, Date period) {
        if (base.equals(period) || period.before(base)) {
            
            return Rank.INDEFINITE;
        } else if (DateUtil.isSameDay(base, period)) {
            
            Date thresholdForSRank = this.toThresholdDateTimeForSRank(base);
            return period.before(thresholdForSRank) ? Rank.S : Rank.A;
        } else {
            
            Date thresholdForBRank = this.toThresholdDateTimeForBRank(base);
            return period.before(thresholdForBRank) ? Rank.B : Rank.C;
        }
    }
    
    private Date toThresholdDateTimeForSRank(Date base) {
        return DateUtil.addHours(base, 3);
    }
    
    private Date toThresholdDateTimeForBRank(Date base) {
        Date truncatedBase = DateUtil.truncate(base, Calendar.DATE);
        return DateUtil.addDays(truncatedBase, 7);
    }
    
    /**
     * この緊急度の期限のコピーを取得します。
     * 
     * @return 期限
     * @throws UnsupportedOperationException 期限が設定されていない場合
     */
    public Date getPeriod() {
        if (this.rank == null) {
            throw new UnsupportedOperationException();
        }
        
        return new Date(period.getTime());
    }

    @Override
    public Rank getRank() {
        return this.rank;
    }
}