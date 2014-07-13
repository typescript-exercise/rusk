package rusk.domain.task;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class PriorityTest {
    
    public static class 緊急度S {
        
        private Urgency urgency = Urgency.RANK_S;
        
        @Test
        public void 重要度Sの場合_優先度はSとなる() {
            // exercise
            Priority priority = new Priority(urgency, Importance.S);
            
            // verify
            assertThat(priority.is(Rank.S), is(true));
        }

        @Test
        public void 重要度Aの場合_優先度はSとなる() {
            // exercise
            Priority priority = new Priority(urgency, Importance.A);
            
            // verify
            assertThat(priority.is(Rank.S), is(true));
        }

        @Test
        public void 重要度Bの場合_優先度はAとなる() {
            // exercise
            Priority priority = new Priority(urgency, Importance.B);
            
            // verify
            assertThat(priority.is(Rank.A), is(true));
        }

        @Test
        public void 重要度Cの場合_優先度はCとなる() {
            // exercise
            Priority priority = new Priority(urgency, Importance.C);
            
            // verify
            assertThat(priority.is(Rank.C), is(true));
        }
    }
    
    public static class 緊急度A {
        
        private Urgency urgency = Urgency.RANK_A;
        
        @Test
        public void 重要度Sの場合_優先度はSとなる() {
            // exercise
            Priority priority = new Priority(urgency, Importance.S);
            
            // verify
            assertThat(priority.is(Rank.S), is(true));
        }

        @Test
        public void 重要度Aの場合_優先度はSとなる() {
            // exercise
            Priority priority = new Priority(urgency, Importance.A);
            
            // verify
            assertThat(priority.is(Rank.S), is(true));
        }

        @Test
        public void 重要度Bの場合_優先度はAとなる() {
            // exercise
            Priority priority = new Priority(urgency, Importance.B);
            
            // verify
            assertThat(priority.is(Rank.A), is(true));
        }

        @Test
        public void 重要度Cの場合_優先度はCとなる() {
            // exercise
            Priority priority = new Priority(urgency, Importance.C);
            
            // verify
            assertThat(priority.is(Rank.C), is(true));
        }
    }
    
    public static class 緊急度B {
        
        private Urgency urgency = Urgency.RANK_B;
        
        @Test
        public void 重要度Sの場合_優先度はaとなる() {
            // exercise
            Priority priority = new Priority(urgency, Importance.S);
            
            // verify
            assertThat(priority.is(Rank.A), is(true));
        }

        @Test
        public void 重要度Aの場合_優先度はAとなる() {
            // exercise
            Priority priority = new Priority(urgency, Importance.A);
            
            // verify
            assertThat(priority.is(Rank.A), is(true));
        }

        @Test
        public void 重要度Bの場合_優先度はBとなる() {
            // exercise
            Priority priority = new Priority(urgency, Importance.B);
            
            // verify
            assertThat(priority.is(Rank.B), is(true));
        }

        @Test
        public void 重要度Cの場合_優先度はCとなる() {
            // exercise
            Priority priority = new Priority(urgency, Importance.C);
            
            // verify
            assertThat(priority.is(Rank.C), is(true));
        }
    }
    
    public static class 緊急度C {
        
        private Urgency urgency = Urgency.RANK_C;
        
        @Test
        public void 重要度Sの場合_優先度はBとなる() {
            // exercise
            Priority priority = new Priority(urgency, Importance.S);
            
            // verify
            assertThat(priority.is(Rank.B), is(true));
        }

        @Test
        public void 重要度Aの場合_優先度はBとなる() {
            // exercise
            Priority priority = new Priority(urgency, Importance.A);
            
            // verify
            assertThat(priority.is(Rank.B), is(true));
        }

        @Test
        public void 重要度Bの場合_優先度はCとなる() {
            // exercise
            Priority priority = new Priority(urgency, Importance.B);
            
            // verify
            assertThat(priority.is(Rank.C), is(true));
        }

        @Test
        public void 重要度Cの場合_優先度はCとなる() {
            // exercise
            Priority priority = new Priority(urgency, Importance.C);
            
            // verify
            assertThat(priority.is(Rank.C), is(true));
        }
    }
    

}
