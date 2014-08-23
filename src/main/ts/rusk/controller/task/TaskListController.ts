/// <reference path="../../resource/task/TaskResource.ts" />

module rusk {
    import TaskResource = rusk.resource.task.TaskResource;
    
    angular
    .module('rusk')
    .controller('TaskListController', [
        '$scope', 'taskResource',
        ($scope, taskResource : TaskResource) => {
            $scope.inquireTaskList = inquireTaskList;
            
            inquireTaskList();
            
            function inquireTaskList() {
                taskResource.inquireList({
                    onLoadComplete: taskList => {
                        $scope.taskList = taskList;
                        $scope.inWorking = taskList.taskInWorking;
                    }
                });
            }
        }
    ]);
}