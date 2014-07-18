angular
.module('rusk')
.controller('RegisterTaskController', [
    '$scope', 'taskResource',
    ($scope, taskResource : rusk.resource.task.TaskResource) => {
        _.extend($scope, {
            register: () => {
                var modal : rusk.view.RegisterTaskModal = $scope.registerModal;
                var task = modal.getTask();
                
                taskResource.register(task)
                    .success(() => {
                        modal.clearForms();
                        modal.close();
                        $scope.inquireTaskList();
                    });
            }
        });
    }
]);