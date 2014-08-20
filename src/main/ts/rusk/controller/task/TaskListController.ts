angular
.module('rusk')
.controller('TaskListController', [
    '$scope', 'taskResource',
    ($scope, taskResource : rusk.resource.task.TaskResource) => {
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