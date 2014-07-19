angular
.module('rusk')
.directive('ruskRegisterTask', [
'$filter', 'taskResource',
($filter, taskResource : rusk.resource.task.TaskResource) => {
    return {
        restrict: 'E',
        replace: true,
        scope: true,
        templateUrl: 'directives/RegisterTask.html',
        link: ($scope, $element, $attr) => {
            
            var modal = new rusk.view.primitive.Dialog($element);
            var form = createForm($scope, $element);
            
            modal.onShown(() => {
                form.reset();
            });
            
            $scope.register = () => {
                var task = form.getTask();
                
                taskResource.register(task)
                    .success(() => {
                        form.reset();
                        modal.close();
                        $scope.inquireTaskList(); // TaskListController に宣言されている
                    });
            }
        }
    };
    
    function createForm($scope, $element) : rusk.view.form.RegisterTaskForm {
        var selectors = {
            title: '#title',
            period: '#period',
            importance: '#importance',
            detail: '#detail'
        };
        
        return new rusk.view.form.RegisterTaskForm($scope, $element.find('form'), selectors);
    }
}]);
