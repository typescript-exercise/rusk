angular
.module('rusk')
.controller('TaskListController', [
    '$scope', 'taskListResource',
    ($scope, taskListResource : rusk.resource.list.TaskListResource) => {
        $scope.inquireTaskList = inquireTaskList;
        
        inquireTaskList();
        
        function inquireTaskList() {
        console.log('inquireTaskList');
            taskListResource.inquire( taskList => {
                $scope.taskList = taskList;
                $scope.inWorking = taskList.taskInWorking;
            });
        }
    }
]);