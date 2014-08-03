package rusk.domain.task;


public enum StatusForm {
    UNSTARTED {
        @Override
        public Task switchTaskStatus(Task task) {
            throw new UnsupportedOperationException();
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
