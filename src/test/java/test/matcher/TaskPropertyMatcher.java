package test.matcher;

import java.util.Objects;

import rusk.domain.task.Task;

public enum TaskPropertyMatcher {
    ID {
        @Override
        public void verify(Task actual, Task expected) throws NotMatchException {
            if (!Objects.equals(actual.getId(), expected.getId())) {
                throw new NotMatchException("ID", expected.getId());
            }
        }
    },
    TITLE {
        @Override
        public void verify(Task actual, Task expected) throws NotMatchException {
            if (!Objects.equals(actual.getTitle(), expected.getTitle())) {
                throw new NotMatchException("Title", expected.getTitle());
            }
        }
    },
    DETAIL {
        @Override
        public void verify(Task actual, Task expected) throws NotMatchException {
            if (!Objects.equals(actual.getDetail(), expected.getDetail())) {
                throw new NotMatchException("Detail", expected.getDetail());
            }
        }
    },
    REGISTERED_DATE {
        @Override
        public void verify(Task actual, Task expected) throws NotMatchException {
            if (!Objects.equals(actual.getRegisteredDate(), expected.getRegisteredDate())) {
                throw new NotMatchException("RegisteredDate", expected.getRegisteredDate());
            }
        }
    },
    PRIORITY {
        @Override
        public void verify(Task actual, Task expected) throws NotMatchException {
            if (!Objects.equals(actual.getPriority(), expected.getPriority())) {
                throw new NotMatchException("Priority", expected.getPriority());
            }
        }
    },
    WORK_TIMES {
        @Override
        public void verify(Task actual, Task expected) throws NotMatchException {
            if (!Objects.equals(actual.getWorkTimes(), expected.getWorkTimes())) {
                throw new NotMatchException("WorkTimes", expected.getWorkTimes());
            }
        }
    },
    COMPLETED_DATE {
        @Override
        public void verify(Task actual, Task expected) throws NotMatchException {
            if (!Objects.equals(actual.getCompletedDate(), expected.getCompletedDate())) {
                throw new NotMatchException("CompletedDate", expected.getCompletedDate());
            }
        }
    }
    ;
    
    abstract public void verify(Task actual, Task expected) throws NotMatchException;
}
