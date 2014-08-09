module rusk {
    export module resource {
        export module task {
            export class TaskResource {
                private $http : ng.IHttpService;
                
                constructor($http : ng.IHttpService) {
                    this.$http = $http;
                }
                
                register(task : any) : ng.IHttpPromise<any> {
                    return this.$http.post('rest/task', task);
                }
                
                inquire(id : number) : ng.IHttpPromise<any> {
                    return this.$http.get('rest/task/' + id);
                }
                
                remove(id : number, onSuccess: Function, onNotFoundError : Function) : void {
                    this.$http.delete('rest/task/' + id, {overrideInterceptor: {
                        404: onNotFoundError
                    }}).success(() => {
                        onSuccess();
                    });
                }
                
                switchStatus(params, onSuccess: Function, onNotFoundError : Function) : void {
                    this.$http.put('rest/task/'+ params.id + '/status', {
                        status: params.status,
                        lastUpdateDate: params.lastUpdateDate
                    }, {
                        overrideInterceptor: {
                            404: onNotFoundError
                        }
                    }).success(() => {
                        onSuccess();
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
