/// <reference path="../resource/task/TaskResource.ts" />
/// <reference path="../view/primitive/Dialog.ts" />
/// <reference path="../view/form/RegisterTaskForm.ts" />

module ruks {
    import TaskResource = rusk.resource.task.TaskResource;
    import Dialog = rusk.view.primitive.Dialog;
    import RegisterTaskForm = rusk.view.form.RegisterTaskForm;
    
    angular
    .module('rusk')
    .directive('ruskRegisterTask', [
    '$filter', 'taskResource',
    ($filter, taskResource : TaskResource) => {
        return {
            restrict: 'E',
            replace: true,
            scope: {
                onRegistered: '&'
            },
            templateUrl: 'directives/RegisterTask.html',
            link: ($scope, $element, $attr) => {
                
                var modal = new Dialog($element);
                var form = createForm($scope, $element);
                
                modal.onShown(() => {
                    form.reset();
                });
                
                $scope.register = () => {
                    if (form.isValid()) {
                        var task = form.getTask();
                        
                        taskResource.register(task)
                            .success(() => {
                                form.reset();
                                modal.close();
                                $scope.onRegistered();
                            });
                    }
                }
            }
        };
        
        function createForm($scope, $element) : RegisterTaskForm {
            return new RegisterTaskForm({
                baseEelement: $element,
                scope: $scope,
                title: {
                    selector: '#title',
                    name: 'title'
                },
                period: {
                    selector: '#period'
                },
                importance: {
                    name: 'importance'
                },
                detail: {
                    selector: '#detail',
                    name: 'detail'
                },
                form: {
                    selector: 'form'
                }
            });
        }
    }]);

}