// <reference path="../../resource/task/TaskResource.ts" />

module rusk {
    import TaskResource = rusk.resource.task.TaskResource;
    
    angular
    .module('rusk')
    .controller('WorkTimeController', [
        '$scope', 'taskResource', '$routeParams',
        ($scope, taskResource : TaskResource, $routeParams) => {
            
            taskResource.inquire($routeParams.id)
                .success((task) => {
                    $scope.task = task;
                });
            
        }
    ]);
}