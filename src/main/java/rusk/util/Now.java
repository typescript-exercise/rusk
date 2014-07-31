package rusk.util;

import java.util.Date;

/**
 * 現在時刻を表す{@link Date} オブジェクトを取得します。
 * <p>
 * このクラスは、現在時刻を使用するテストを簡単にスタブ化できるようにするために存在します。<br>
 * プログラム中で現在時刻を取得する必要がある場合は必ずこのクラスから値を取得するようにします。<br>
 * これによって、テストではこのクラスの{@link #get()} メソッドをスタブ化するだけで簡単にテストができるようになります。
 */
public class Now {
    
    /**
     * 現在時刻を取得する。
     * 
     * @return 現在時刻
     */
    public static Date get() {
        return new Date();
    }
}
