angular
.module('rusk')
.controller('TaskDetailController', [
    '$scope', '$routeParams', '$location', 'taskResource', 'removeTaskService',
    ($scope, $routeParams, $location : ng.ILocationService, taskResource : rusk.resource.task.TaskResource, removeTaskService) => {
        var form : rusk.view.form.TaskDetailForm;
        
        taskResource.inquire($routeParams.id)
            .success((task) => {
                $scope.task = task;
            });
        
        $scope.init = () => {
            form = createTaskDetailForm($scope);
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
        
        $scope.modify = () => {
            if (!form.isValid()) {
                return;
            }
            
            var params = _.extend({
                              id: $scope.task.id,
                              lastUpdateDate: $scope.task.updateDate
                          }, form.getParams());
            
            taskResource.modify(params, 
                function onSuccess() {
                    toastr.success('「' + $scope.task.title + '」を更新しました。');
                    $location.path('/');
                },
                function onNotFoundError() {
                    alert('このタスクは既に削除されています。');
                });
        };
        
        function createTaskDetailForm($scope) : rusk.view.form.TaskDetailForm {
            
            var title = new rusk.view.primitive.TextBox($scope.task, $('#title'), 'title');
            
            appendCurrentTaskStatus($scope);
            var status = new rusk.view.primitive.SelectBox($scope.task, 'status');
            
            var period = new rusk.view.primitive.DateTime($('#period'), {
                defaultDate: $scope.task.priority.urgency.period,
                minDate: $scope.task.registeredDate
            });
            
            var importance = new rusk.view.primitive.SelectBox($scope.task.priority, 'importance');
            
            var detail = new rusk.view.primitive.TextArea($scope.task, $('#detail'), 'detail');
            
            var validateOptions = rusk.view.form.TaskValidateOptionBuilder.create().title().period().importance().detail().build();
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
        
        function appendCurrentTaskStatus($scope) {
            $scope.task.enableToSwitchStatusList.unshift($scope.task.status);
        }
    }
]);