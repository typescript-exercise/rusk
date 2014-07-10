package rusk.util;

import java.util.Date;

/**
 * 現在時刻を表す{@link Date} オブジェクトを取得します。
 * <p>
 * このクラスは、今日日付を使用するテストを簡単にスタブ化できるようにするために存在します。<br>
 * プログラム中で今日日付を取得する必要がある場合は必ずこのクラスから値を取得するようにします。<br>
 * これによって、テストではこのクラスの{@link #get()} メソッドをスタブ化するだけで簡単にテストができるようになります。
 */
public class Today {
    
    /**
     * 今日日付を取得する。
     * 
     * @return 今日日付
     */
    public static Date get() {
        return new Date();
    }
}
