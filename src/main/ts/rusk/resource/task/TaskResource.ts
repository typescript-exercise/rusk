/// <reference path="../../model/models.ts" />

module rusk {
    export module resource {
        export module task {
            import TaskList = rusk.model.list.TaskList;
            
            export class TaskResource {
                private $http : ng.IHttpService;
                
                constructor($http : ng.IHttpService) {
                    this.$http = $http;
                }
                
                register(task : any) : ng.IHttpPromise<any> {
                    return this.$http.post('rest/task', task);
                }
                
                inquireList(param : {onLoadComplete : (taskList : TaskList) => void}) : void {
                    this.$http.get('rest/task-list', {}).success(param.onLoadComplete);
                }
                
                inquire(id : number) : ng.IHttpPromise<any> {
                    return this.$http.get('rest/task/' + id);
                }
                
                remove(param : {
                    id : number;
                    onSuccess: Function;
                    onNotFoundError: Function;
                }) : void {
                    this.$http.delete('rest/task/' + param.id, {overrideInterceptor: {
                        404: param.onNotFoundError
                    }}).success(() => {
                        param.onSuccess();
                    });
                }
                
                switchStatus(param: {
                    id: number;
                    status: string;
                    lastUpdateDate: number;
                    onSuccess: Function;
                    onNotFoundError : Function;
                }) : void {
                    var putData = {
                        status: param.status,
                        lastUpdateDate: param.lastUpdateDate
                    };
                    
                    var conf = {
                        overrideInterceptor: {
                            404: param.onNotFoundError
                        }
                    };
                    
                    this.$http
                        .put('rest/task/'+ param.id + '/status', putData, conf)
                        .success(() => {
                            param.onSuccess();
                        });
                }
                
                modify(param: {
                    putData: any;
                    onSuccess: Function;
                    onNotFoundError : Function;
                }) : void {
                    var conf = {
                        overrideInterceptor: {
                            404: param.onNotFoundError
                        }
                    };
                    
                    this.$http
                        .put('rest/task/'+ param.putData.id, param.putData, conf)
                        .success(() => {
                            param.onSuccess();
                        });
                }
                
                registerWorkTime(param) : void {
                    var conf = {
                        overrideInterceptor: {
                            400: param.onBadRequest
                        }
                    };
                    
                    this.$http
                        .post('rest/task/'+ param.taskId + '/work-time', param.postData, conf)
                        .success(() => {
                            param.onSuccess();
                        });
                }
                
                removeWorkTime(param) : void {
                    this.$http
                        .delete('rest/task/'+ param.taskId + '/work-time/' + param.workTimeId)
                        .success(() => {
                            param.onSuccess();
                        });
                }
            }
        }
    }
}

angular
.module('rusk')
.factory('taskResource', [
    '$http',
    ($http) => new rusk.resource.task.TaskResource($http)
]);
