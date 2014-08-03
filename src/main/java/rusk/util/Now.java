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
    private static Date get() {
        return new Date();
    }
    
    public static Date getForStartTime() {
        return get();
    }
    
    public static Date getForEndTime() {
        return get();
    }
    
    public static Date getForRegisteredDate() {
        return get();
    }
    
    public static Date getForCompletedDate() {
        return get();
    }
    
    public static Date getForInquireCompletedTaskInToday() {
        return get();
    }
    
    public static Date getForUrgency() {
        return get();
    }
}
