package rusk.domain.task.exception;

import rusk.application.exception.ConcurrentUpdateException;

/**
 * 同時更新などが原因で、本来は実行できない状態の変更が行われたことを表す例外。
 */
public class UnexpectedSwitchStatusException extends ConcurrentUpdateException {
    private static final long serialVersionUID = 1L; 
}
