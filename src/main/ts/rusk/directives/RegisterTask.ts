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
        var title = new rusk.view.primitive.TextBox($scope, $element.find('#title'), 'title');
        var period = new rusk.view.primitive.DateTime($element.find('#period'));
        var importance = new rusk.view.primitive.SelectBox($scope, 'importance');
        var detail = new rusk.view.primitive.TextArea($scope, $element.find('#detail'), 'detail');
        
        var validateOptions = rusk.config.validation.TaskValidateOptionBuilder.create().title().period().importance().detail().build();
        var form = new rusk.view.primitive.Form($element.find('#register-task'), validateOptions);
        
        return new rusk.view.form.RegisterTaskForm({
            title: title,
            period: period,
            importance: importance,
            detail: detail,
            form: form
        });
    }
}]);
