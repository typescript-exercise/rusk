angular
.module('rusk')
.controller('TaskListController', [
    '$scope', 'taskListResource',
    ($scope, taskListResource : rusk.resource.task.TaskListResource) => {
        $scope.inquireTaskList = inquireTaskList;
        
        inquireTaskList();
        
        function inquireTaskList() {
            taskListResource.inquire( taskList => {
                $scope.taskList = taskList;
                $scope.inWorking = taskList.taskInWorking;
            });
        }
    }
]);