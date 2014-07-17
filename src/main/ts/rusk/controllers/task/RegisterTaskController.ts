angular
.module('rusk')
.controller('RegisterTaskController', [
    '$scope', '$filter', 'taskResource',
    ($scope, $filter, taskResource : rusk.resource.task.TaskResource) => {
        var builder = rusk.model.task.TaskValidateOptionBuilder.create();
        var validateOption = builder.title().period().importance().detail().build();
        
        _.extend($scope, {
            importance: 'B',
            
            datepickerOption: {
                value: $filter('date')(new Date(), 'yyyy/MM/dd HH:mm')
            },
            
            validateOption: {
                rules: validateOption
            },
            
            register: () => {
                var task = {
                    title: $scope.title,
                    period: $('#period').val(),
                    importance: $scope.importance,
                    detail: $scope.detail
                };
                
                taskResource.register(task)
                    .success(() => {
                        $scope.closeModal();
                        $scope.inquireTaskList();
                    });
            }
        });
    }
]);