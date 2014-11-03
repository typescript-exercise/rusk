/// <reference path="../../resource/task/TaskResource.ts" />

module rusk {
    import TaskResource = rusk.resource.task.TaskResource;
    
    angular
    .module('rusk')
    .controller('DailyHistoryController', [
        '$scope', 'taskResource',
        ($scope, taskResource : TaskResource) => {
            
        }
    ]);
}