package rusk.domain.task;

import java.util.Date;

import rusk.common.util.DateUtil;

public class CompletedUrgency extends Urgency {
    
    CompletedUrgency(Date period) {
        super(DateUtil.create("1970-01-01"), period);
    }
}
