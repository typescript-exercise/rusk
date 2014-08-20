module rusk {
    export module model {
        export module list {
            export interface TaskList {
                completeTasks : Array<task.Task>;
                uncompleteTasks : Array<task.Task>;
                taskInWorking : task.Task;
            }
        }
        
        export module task {
            export interface Task {
                title : string;
            }
        }
    }
}
