package rusk.domain.task;

import rusk.domain.task.exception.UnexpectedSwitchStatusException;


public enum Status {
    UNSTARTED {
        @Override
        public Task switchTaskStatus(Task task) {
            throw new UnexpectedSwitchStatusException();
        }
    },
    STOPPED {
        @Override
        public Task switchTaskStatus(Task task) {
            return task.switchToStoppedTask();
        }
    },
    IN_WORKING {
        @Override
        public Task switchTaskStatus(Task task) {
            return task.switchToInWorkingTask();
        }
    },
    COMPLETE {
        @Override
        public Task switchTaskStatus(Task task) {
            return task.switchToCompletedTask();
        }
    }
    ;
    
    public abstract Task switchTaskStatus(Task task);
}
