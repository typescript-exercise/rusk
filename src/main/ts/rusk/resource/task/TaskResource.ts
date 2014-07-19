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