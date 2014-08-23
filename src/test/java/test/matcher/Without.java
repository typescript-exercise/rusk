package test.matcher;

import static java.util.stream.Collectors.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Without {
    
    public static final Without EMPTY = new Without();
    
    private List<TaskPropertyMatcher> withoutMatchers;
    
    private Without() {
        this.withoutMatchers = Collections.emptyList();
    }
    
    public Without(List<TaskPropertyMatcher> matchers) {
        this.withoutMatchers = matchers;
    }
    
    public List<TaskPropertyMatcher> apply(TaskPropertyMatcher[] matchers) {
        return Stream.of(matchers).filter(matcher -> !this.withoutMatchers.contains(matcher)).collect(toList());
    }
}
