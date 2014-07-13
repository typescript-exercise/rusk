angular
.module('rusk')
.controller('TaskListController', [
    '$scope', 'taskListResource',
    function($scope, taskListResource : rusk.resource.list.TaskListResource) {
        
        $scope.inquireTasList = inquireTaskList;
        
        inquireTaskList();
        
        function inquireTaskList() {
            taskListResource.inquire( taskList => {
                $scope.taskList = taskList;
                $scope.inWorking = taskList.taskInWorking;
            });
        }
    }
]);