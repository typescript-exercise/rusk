package rusk.domain.task;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import rusk.util.Immutable;

/**
 * 状態
 */
@Immutable
public enum Status {
    /**未着手*/
    UNSTARTED,
    /**作業中*/
    IN_WORKING,
    /**中断*/
    STOPPED,
    /**完了*/
    COMPLETE,
    ;
}
