package rusk.domain.task;

import java.util.stream.Stream;

/**
 * 自分のランクと、指定したランクを比較するインターフェース。
 */
public interface RankComparator {

    /**
     * このオブジェクトのランクを確認します。
     * 
     * @param rank ランク
     * @return このオブジェクトのランクが、指定したランクと等しい場合は true
     */
    default boolean is(Rank rank) {
        return this.getRank() == rank;
    }
    
    /**
     * このオブジェクトが、指定したランクのいずれかに一致することを確認します。
     * 
     * @param ranks 比較するランク
     * @return このオブジェクトのランクが、指定したランクのいずれかに一致する場合は true
     */
    default boolean anyOf(Rank... ranks) {
        return Stream.of(ranks).anyMatch(rank -> this.is(rank));
    }
    
    /**
     * このオブジェクトのランクを取得します。
     * 
     * @return このオブジェクトのランク
     */
    Rank getRank();
}
