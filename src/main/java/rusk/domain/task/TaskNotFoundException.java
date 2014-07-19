package rusk.domain.task;

import rusk.domain.EntityNotFoundException;

public class TaskNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;
    
    public TaskNotFoundException(long id) {
        super("ID = " + id + " のタスクは存在しません");
    }
}
