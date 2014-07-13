package rusk.domain.task;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 優先度
 * <p>
 * 優先度は、以下のルールに従い緊急度と重要度から導出されます。
 * <table border="1">
 *     <tr>
 *         <th colspan="2" rowspan="2">優先度</th>
 *         <th colspan="4">緊急度</th>
 *     </tr>
 *     <tr>
 *         <th>S</th>
 *         <th>A</th>
 *         <th>B</th>
 *         <th>C</th>
 *     </tr>
 *     <tr>
 *         <th rowspan="4">重要度</th>
 *         <th>S</th>
 *         <td>S</td>
 *         <td>S</td>
 *         <td>A</td>
 *         <td>B</td>
 *     </tr>
 *     <tr>
 *         <th>A</th>
 *         <td>S</td>
 *         <td>S</td>
 *         <td>A</td>
 *         <td>B</td>
 *     </tr>
 *     <tr>
 *         <th>B</th>
 *         <td>A</td>
 *         <td>A</td>
 *         <td>B</td>
 *         <td>C</td>
 *     </tr>
 *     <tr>
 *         <th>C</th>
 *         <td>C</td>
 *         <td>C</td>
 *         <td>C</td>
 *         <td>C</td>
 *     </tr>
 * </table>
 */
public class Priority implements RankComparator, Comparable<Priority> {
    private final Urgency urgency;
    private final Importance importance;
    private final Rank rank;

    /**
     * このコンストラクタは、フレームワークのために存在します。
     */
    @Deprecated
    public Priority() {
        this.urgency = null;
        this.importance = null;
        this.rank = null;
    }
    
    public Priority(Urgency urgency, Importance importance) {
        this.urgency = urgency;
        this.importance = importance;
        this.rank = this.isolateRank(urgency, importance);
    }
    
    private Rank isolateRank(Urgency urgency, Importance importance) {
        if (urgency.any(Rank.S, Rank.A)) {
            if (importance.any(Rank.S, Rank.A)) {
                return Rank.S;
            } else if (importance.is(Rank.B)) {
                return Rank.A;
            } else {
                return Rank.C;
            }
        } else if (urgency.is(Rank.B)) {
            if (importance.any(Rank.S, Rank.A)) {
                return Rank.A;
            } else if (importance.is(Rank.B)) {
                return Rank.B;
            } else {
                return Rank.C;
            }
        } else {
            if (importance.any(Rank.S, Rank.A)) {
                return Rank.B;
            } else {
                return Rank.C;
            }
        }
    }

    public Importance getImportance() {
        return importance;
    }

    public Urgency getUrgency() {
        return urgency;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    @Override
    public Rank getRank() {
        return this.rank;
    }

    @Override
    public int compareTo(Priority other) {
        return this.rank.compareTo(other.rank);
    }
}
