package rusk.domain.task;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import rusk.util.Immutable;

/**
 * 重要度
 */
@Immutable
public enum Importance implements RankComparator {
    C(Rank.C),
    B(Rank.B),
    A(Rank.A),
    S(Rank.S),
    ;
    
    private final Rank rank;
    
    private Importance(Rank rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    @Override
    public Rank getRank() {
        return this.rank;
    }
}
