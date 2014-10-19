/// <reference path="../../resource/task/TaskResource.ts" />
/// <reference path="../../view/form/RegisterWorkTimeForm.ts" />
/// <reference path="../../view/form/ModifyWorkTimeForm.ts" />

module rusk {
    import TaskResource = rusk.resource.task.TaskResource;
    import RegisterWorkTimeForm = rusk.view.form.RegisterWorkTimeForm;
    import ModifyWorkTimeForm = rusk.view.form.ModifyWorkTimeForm;
    
    angular
    .module('rusk')
    .controller('WorkTimeController', [
        '$scope', 'taskResource', '$routeParams',
        ($scope, taskResource : TaskResource, $routeParams) => {
            var modifyForm = new ModifyWorkTimeForm({
                startTime: {
                    selector: '#start-time-modify'
                },
                endTime: {
                    selector: '#end-time-modify'
                },
                form: {
                    selector: '#modify-work-time-form'
                }
            });
            
            _.extend($scope, {
                load: () => {
                    load();
                },
                
                clearErrorMessage: () => {
                    $scope.errorMessage = '';
                },
                
                clearModifyErrorMessage: () => {
                    $scope.modifyErrorMessage = '';
                },
                
                setErrorMessage: (message) => {
                    $scope.errorMessage = message;
                },
                
                onModifyDialogOpened: (workTime) => {
                    $scope.selectedWorkTime = workTime;
                    modifyForm.setStartTime(workTime.startTime);
                    modifyForm.setEndTime(workTime.endTime);
                    modifyForm.resetValidation();
                    $scope.clearModifyErrorMessage();
                    modifyForm.clearErrorStyle();
                },
                
                modifyWorkTime: () => {
                    $scope.clearModifyErrorMessage();
                    
                    if (!modifyForm.isValid()) {
                        return false;
                    }
                    
                    var putData = $.extend({}, $scope.selectedWorkTime, {
                        startTime: modifyForm.getStartTime(),
                        endTime: modifyForm.getEndTime(),
                        taskId: $routeParams.id
                    });
                    
                    taskResource.modifyWorkTime({
                        putData: putData,
                        onSuccess: () => {
                            $('#modifyWorkTimeModal').modal('hide');
                            toastr.success('作業時間を更新しました。');
                            load();
                        },
                        onBadRequestError: (error) => {
                            $scope.modifyErrorMessage = error.message;
                        },
                        onConflictError: () => {
                            $scope.modifyErrorMessage = 'この作業時間は同時更新されています。画面を更新してください。';
                        },
                        onNotFoundError: () => {
                            $scope.modifyErrorMessage = 'この作業時間は既に削除されています。画面を更新してください。';
                        }
                    });
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
    .controller('DeleteWorkTimeController', [
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
                            },
                            onNotFoundError: () => {
                                alert('指定した作業時間は既に削除されています。画面を更新してください。');
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
