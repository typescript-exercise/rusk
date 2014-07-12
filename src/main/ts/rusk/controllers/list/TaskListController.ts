angular
.module('rusk')
.controller('TaskListController', [
    '$scope', 'taskListResource',
    function($scope, taskListResource : rusk.resource.list.TaskListResource) {
        $scope.inquireTasList = () => {
            taskListResource.inquire((taskList : rusk.model.list.TaskList) => {
                console.dir(taskList);
            });
        };
    }
]);