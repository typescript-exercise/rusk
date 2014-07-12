module rusk {
    export module resource {
        export module list {
            export class TaskListResource {
                private $http : ng.IHttpService;
                
                constructor($http : ng.IHttpService) {
                    this.$http = $http;
                }
                
                inquire(callback : (taskList : rusk.model.list.TaskList) => void) : void {
                    this.$http.get('rest/task-list', {})
                        .success((data : rusk.model.list.TaskList) => {
                            callback(data);
                        });
                }
            }
        }
    }
}

angular
.module('rusk')
.factory('taskListResource', [
    '$http',
    ($http) => {
        return new rusk.resource.list.TaskListResource($http);
    }
]);
