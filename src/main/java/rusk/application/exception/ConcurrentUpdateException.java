package rusk.application.exception;

/**
 * 更新対象のデータが同時更新されている際にスローされる例外
 */
public class ConcurrentUpdateException extends RuntimeException {
    private static final long serialVersionUID = 1L;
}
