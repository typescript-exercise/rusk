/// <reference path="../../resource/task/TaskResource.ts" />
/// <reference path="../../view/form/TaskDetailForm.ts" />

module ruks {
    import TaskResource = rusk.resource.task.TaskResource;
    import TaskDetailForm = rusk.view.form.TaskDetailForm;
    
    angular
    .module('rusk')
    .controller('TaskDetailController', [
        '$scope', '$routeParams', '$location', 'taskResource', 'removeTaskService',
        ($scope, $routeParams, $location : ng.ILocationService, taskResource : TaskResource, removeTaskService) => {
            var form : TaskDetailForm;
            
            taskResource.inquire($routeParams.id)
                .success((task) => {
                    $scope.task = task;
                });
            
            $.extend($scope, {
                init: () => {
                    appendCurrentTaskStatusForPulldown($scope);
                    form = createTaskDetailForm($scope);
                },
                
                remove: () => {
                    removeTaskService.remove({
                        id: $scope.task.id,
                        title: $scope.task.title,
                        onRemove: () => {
                            $location.path('/');
                        }
                    });
                },
                
                modify: () => {
                    if (!form.isValid()) {
                        return;
                    }
                    
                    var params = _.extend({
                                      id: $scope.task.id,
                                      lastUpdateDate: $scope.task.updateDate
                                  }, form.getParams());
                    
                    taskResource.modify({
                        putData: params,
                        onSuccess: () => {
                            toastr.success('「' + $scope.task.title + '」を更新しました。');
                            $location.path('/');
                        },
                        onNotFoundError: () => {
                            alert('このタスクは既に削除されています。');
                        }
                    });
                }
            });
            
            function appendCurrentTaskStatusForPulldown($scope) {
                $scope.task.enableToSwitchStatusList.unshift($scope.task.status);
            }
            
            function createTaskDetailForm($scope) : TaskDetailForm {
                return new TaskDetailForm({
                    task: $scope.task,
                    title: {
                        selector: '#title',
                        name: 'title'
                    },
                    status: {
                        name: 'status'
                    },
                    period: {
                        selector: '#period',
                        options: {
                            defaultDate: $scope.task.priority.urgency.period,
                            minDate: $scope.task.registeredDate
                        }
                    },
                    importance: {
                        name: 'importance'
                    },
                    detail: {
                        selector: '#detail',
                        name: 'detail'
                    },
                    form: {
                        selector: '#task-detail'
                    }
                });
            }
        }
    ]);
}