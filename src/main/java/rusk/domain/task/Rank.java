package rusk.domain.task;

/**
 * S ～ C のランクを表す列挙型。
 */
public enum Rank {
    C, B, A, S,
    /**不定。これは、ランクが特定の値に定まらない状態であることを示します。*/
    INDEFINITE
}
