declare module rusk {
    export module model {
        export module list {
            export interface TaskList {
                completedTasks : Array<rusk.model.task.Task>;
                uncompletedTasks : Array<rusk.model.task.Task>;
                taskInWorking : rusk.model.task.Task;
            }
        }
        
        export module task {
            export interface Task {
                title : string;
            }
        }
    }
}