module rusk {
    export module model {
        export var systemInitialized : boolean = false;
        
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
            
            export class TaskValidateOptionBuilder {
                private option = {};
                
                static create() : TaskValidateOptionBuilder {
                    return new TaskValidateOptionBuilder();
                }
                
                title() : TaskValidateOptionBuilder {
                    _.extend(this.option, {
                        title: {
                            required: true,
                            maxlength: 50
                        }
                    });
                    return this;
                }
                
                period() : TaskValidateOptionBuilder {
                    _.extend(this.option, {
                        period: {
                            required: true
                        }
                    });
                    return this;
                }
                
                importance() : TaskValidateOptionBuilder {
                    _.extend(this.option, {
                        importance: {
                            required: true
                        }
                    });
                    return this;
                }
                
                detail() : TaskValidateOptionBuilder {
                    _.extend(this.option, {
                        detail: {
                            maxlength: 1000
                        }
                    });
                    return this;
                }
                
                build() : any {
                    return this.option;
                }
            }
        }
    }
}
