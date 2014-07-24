angular
.module('rusk')
.controller('TaskListController', [
    '$scope', 'taskListResource', 'taskResource',
    ($scope, taskListResource : rusk.resource.list.TaskListResource, taskResource : rusk.resource.task.TaskResource) => {
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