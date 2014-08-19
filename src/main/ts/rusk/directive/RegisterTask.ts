angular
.module('rusk')
.directive('ruskRegisterTask', [
'$filter', 'taskResource',
($filter, taskResource : rusk.resource.task.TaskResource) => {
    return {
        restrict: 'E',
        replace: true,
        scope: {
            onRegistered: '&'
        },
        templateUrl: 'directives/RegisterTask.html',
        link: ($scope, $element, $attr) => {
            
            var modal = new rusk.view.primitive.Dialog($element);
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
    
    function createForm($scope, $element) : rusk.view.form.RegisterTaskForm {
        return new rusk.view.form.RegisterTaskForm({
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
