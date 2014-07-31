package rusk.domain.task;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.Date;

import mockit.NonStrictExpectations;

import org.junit.Before;
import org.junit.Test;

import rusk.util.DateUtil;
import rusk.util.Now;

public class UrgencyTest {

    private Date now = DateUtil.create("2014-01-01 10:00:00");
    
    @Before
    public void setup() {
        new NonStrictExpectations(Now.class) {{
            Now.get(); result = now;
        }};
    }
    
    @Test
    public void 期限が基準日と同じ場合_ランクがSになること() {
        // exercise
        Urgency urgency = new Urgency(now, now);
        
        // verify
        assertThat(urgency.is(Rank.S), is(true));
    }
    
    @Test
    public void 期限が基準日より前の場合_ランクがSになること() {
        // setup
        Date period = DateUtil.addMilliseconds(now, -1);
        
        // exercise
        Urgency urgency = new Urgency(now, period);
        
        // verify
        assertThat(urgency.is(Rank.S), is(true));
    }
    
    @Test
    public void 期限が基準日の３時間後より前の場合_ランクSの緊急度が生成されること() {
        // setup
        Date period = DateUtil.addHours(now, 3);
        period = DateUtil.addMilliseconds(period, -1);
        
        // exercise
        Urgency urgency = new Urgency(now, period);
        
        // verify
        assertThat(urgency.is(Rank.S), is(true));
    }
    
    @Test
    public void 期限が基準日の３時間後の場合_ランクAの緊急度が生成されること() {
        // setup
        Date period = DateUtil.addHours(now, 3);
        
        // exercise
        Urgency urgency = new Urgency(now, period);
        
        // verify
        assertThat(urgency.is(Rank.A), is(true));
    }
    
    @Test
    public void 期限が基準日の次の日の場合_ランクBの緊急度が生成されること() {
        // setup
        Date period = DateUtil.create("2014-01-02 00:00:00");
        
        // exercise
        Urgency urgency = new Urgency(now, period);
        
        // verify
        assertThat(urgency.is(Rank.B), is(true));
    }
    
    @Test
    public void 期限が基準日の１週間後より前の場合_ランクBの緊急度が生成されること() {
        // setup
        Date period = DateUtil.create("2014-01-07 23:59:59");
        
        // exercise
        Urgency urgency = new Urgency(now, period);
        
        // verify
        assertThat(urgency.is(Rank.B), is(true));
    }
    
    @Test
    public void 期限が基準日の１週間後より後の場合_ランクCの緊急度が生成されること() {
        // setup
        Date period = DateUtil.create("2014-01-08 00:00:00");
        
        // exercise
        Urgency urgency = new Urgency(now, period);
        
        // verify
        assertThat(urgency.is(Rank.C), is(true));
    }
    
    @Test
    public void 期限とランクが同じ場合_equalsメソッドはtrueを返すこと() {
        // setup
        Date period = DateUtil.create("2014-01-08 00:00:00");
        
        // exercise
        Urgency one = new Urgency(now, period);
        Urgency other = new Urgency(now, period);
        
        // verify
        assertThat(one, is(other));
    }
}
