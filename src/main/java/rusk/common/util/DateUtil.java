package rusk.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

/**
 * {@link DateUtils} の拡張クラス。
 */
public class DateUtil extends DateUtils {
    
    /**
     * 日付テキストを{@link Date} に変換する。
     * <p>
     * このメソッドは、以下の書式の日付テキストを受け入れます。
     * <ul>
     *   <li>yyyy-MM-dd HH:mm:ss.SSS
     *   <li>yyyy-MM-dd HH:mm:ss
     *   <li>yyyy-MM-dd
     *   <li>yyyy/MM/dd HH:mm:ss.SSS
     *   <li>yyyy/MM/dd HH:mm:ss
     *   <li>yyyy/MM/dd
     * </ul>
     * 
     * @param textDate 日付テキスト
     * @return 変換後の日付
     * @throws RuntimeException 日付変換に失敗した場合
     */
    public static Date create(String textDate) {
        try {
            return parseDate(textDate, new String[]{
                    "yyyy-MM-dd HH:mm:ss.SSS",
                    "yyyy-MM-dd HH:mm:ss",
                    "yyyy-MM-dd",
                    "yyyy/MM/dd HH:mm:ss.SSS",
                    "yyyy/MM/dd HH:mm:ss",
                    "yyyy/MM/dd"
                    });
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static Timestamp createTimestamp(String textDate) {
        return new Timestamp(create(textDate).getTime());
    }
    
    private DateUtil() {}
}
