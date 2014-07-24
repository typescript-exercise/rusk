angular
.module('rusk')
.controller('TaskDetailController', [
    '$scope', '$routeParams', '$location', 'taskResource', 'removeTaskService',
    ($scope, $routeParams, $location : ng.ILocationService, taskResource : rusk.resource.task.TaskResource, removeTaskService) => {
        
        taskResource.inquire($routeParams.id)
            .success((task) => {
                $scope.task = task;
            });
        
        $scope.init = () => {
            var detailForm = createTaskDetailForm($scope);
        };
        
        $scope.remove = () => {
            removeTaskService.remove({
                id: $scope.task.id,
                title: $scope.task.title,
                onRemove: () => {
                    $location.path('/');
                }
            });
        };
        
        function createTaskDetailForm($scope) : rusk.view.form.TaskDetailForm {
            var title = new rusk.view.primitive.TextBox($scope, $('#title'), 'title');
            var status = new rusk.view.primitive.SelectBox($scope, 'status');
            var period = new rusk.view.primitive.DateTime($('#period'), {
                defaultDate: $scope.task.priority.urgency.period,
                minDate: $scope.task.registeredDate
            });
            var importance = new rusk.view.primitive.SelectBox($scope, 'importance');
            var detail = new rusk.view.primitive.TextArea($scope, $('#detail'), 'detail');
            
            var validateOptions = rusk.config.validation.TaskValidateOptionBuilder.create().title().period().importance().detail().build();
            var form = new rusk.view.primitive.Form($('#task-detail'), validateOptions);
            
            return new rusk.view.form.TaskDetailForm({
                title: title,
                status: status,
                period: period,
                importance: importance,
                detail: detail,
                form: form
            });
        }
    }
]);