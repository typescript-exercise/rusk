package rusk.domain.task.exception;

import rusk.domain.EntityNotFoundException;

public class WorkTimeNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public WorkTimeNotFoundException(String message) {
        super(message);
    }
}
