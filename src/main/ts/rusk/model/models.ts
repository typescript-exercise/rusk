module rusk {
    export module model {
        export module list {
            export interface TaskList {
                completeTasks : Array<rusk.model.task.Task>;
                uncompleteTasks : Array<rusk.model.task.Task>;
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
