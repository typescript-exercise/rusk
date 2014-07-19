package rusk.domain.task;

/**
 * S ～ C のランクを表す列挙型。
 */
public enum Rank {
    C, B, A, S,
    /**不定。これは、ランクが特定の値に定まらない状態であることを示します。
     * @deprecated この列挙子は開発中に一時的に用意されたもので、リリースまでに再度必要にならなければ削除されます。
     * */
    @Deprecated
    INDEFINITE
}
