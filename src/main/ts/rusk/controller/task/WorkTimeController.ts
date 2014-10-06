/// <reference path="../../resource/task/TaskResource.ts" />
/// <reference path="../../view/form/RegisterWorkTimeForm.ts" />

module rusk {
    import TaskResource = rusk.resource.task.TaskResource;
    import RegisterWorkTimeForm = rusk.view.form.RegisterWorkTimeForm;
    
    angular
    .module('rusk')
    .controller('WorkTimeController', [
        '$scope', 'taskResource', '$routeParams',
        ($scope, taskResource : TaskResource, $routeParams) => {
            _.extend($scope, {
                load: () => {
                    load();
                },
                
                clearErrorMessage: () => {
                    $scope.errorMessage = '';
                },
                
                setErrorMessage: (message) => {
                    $scope.errorMessage = message;
                }
            });
            
            load();
            
            function load() {
                taskResource.inquire($routeParams.id)
                    .success((task) => {
                        $scope.task = task;
                    });
            }
        }
    ])
    .controller('EditWorkTimeController', [
        '$scope', 'taskResource',
        ($scope, taskResource: TaskResource) => {
            _.extend($scope, {
                
                remove: (taskId, workTimeId) => {
                    $scope.clearErrorMessage();
                    
                    if (confirm('作業時間を削除します。よろしいですか？')) {
                        taskResource.removeWorkTime({
                            taskId: taskId,
                            workTimeId: workTimeId,
                            onSuccess: () => {
                                $scope.load();
                            }
                        });
                    }
                }
            });
        }
    ])
    .controller('RegisterWorkTimeController', [
        '$scope', 'taskResource',
        ($scope, taskResource: TaskResource) => {
            var form = new RegisterWorkTimeForm({
                startTime: {
                    selector: '#start-time'
                },
                endTime: {
                    selector: '#end-time'
                },
                form: {
                    selector: '#register-work-time-form'
                }
            });
            
            _.extend($scope, {
                registerWorkTime: () => {
                    if (!form.isValid()) {
                        return false;
                    }
                    
                    $scope.clearErrorMessage();
                    
                    taskResource.registerWorkTime({
                        taskId: $scope.task.id,
                        postData: form.getParams(),
                        onSuccess: () => {
                            $scope.load();
                        },
                        onBadRequest: (response) => {
                            $scope.setErrorMessage(response.message);
                        }
                    });
                }
            });
        }
    ]);
}
