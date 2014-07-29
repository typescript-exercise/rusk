package rusk.util;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * ラムダ式を利用した実装を簡潔に行うためのラッパーユーティリティ
 */
public class LamdaUtil<T> {
    
//    /**
//     * 指定したリストの各要素をマッピング処理で変換し、各変換結果をリストにして返す。
//     * 
//     * @param list リスト
//     * @param mapper マッピング処理
//     * @return 変換結果
//     */
//    public static <T, R> List<R> map(List<T> list, Function<T, R> mapper) {
//        return list.stream().map(mapper).collect(toList());
//    }
//    
//    /**
//     * 指定したリストから、述語条件に一致する要素だけを抽出する。
//     * 
//     * @param list リスト
//     * @param predicate 述語条件
//     * @return 抽出結果
//     */
//    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
//        return list.stream().filter(predicate).collect(toList());
//    }
//    
//    /**
//     * 指定したリストから、述語条件に一致しない要素だけを抽出する。
//     * 
//     * @param list リスト
//     * @param predicate 述語条件
//     * @return 抽出結果
//     */
//    public static <T> List<T> ignoreFilter(List<T> list, Predicate<T> predicate) {
//        return list.stream().filter(predicate.negate()).collect(toList());
//    }
    
    private List<T> list;
    
    public static <T> LamdaUtil<T> list(List<T> list) {
        return new LamdaUtil<T>(list);
    }
    
    public List<T> filter(Predicate<T> predicate) {
        return this.list.stream().filter(predicate).collect(toList());
    }
    
    public List<T> negateFilter(Predicate<T> predicate) {
        return this.list.stream().filter(predicate.negate()).collect(toList());
    }
    
    public <R> List<R> map(Function<T, R> mapper) {
        return this.list.stream().map(mapper).collect(toList());
    }
    
    private LamdaUtil(List<T> list) {
        this.list = list;
    }
}
