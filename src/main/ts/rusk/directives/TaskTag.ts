angular
.module('rusk')
.directive('ruskTaskTag', [
    'removeTaskService', 'taskResource',
    (removeTaskService, taskResource : rusk.resource.task.TaskResource) => {
    return {
        restrict: 'E',
        replace: true,
        scope: {
            task: '=',
            onRemove: '&',
            onSwitchStatus: '&'
        },
        templateUrl: 'directives/TaskTag.html',
        link: ($scope, $element, $attr) => {
            var task = $scope.task;
            var complete = task.completed;
            
            _.extend($scope, {
                panelClass: {
                    'panel-danger': !complete && task.rankS,
                    'panel-warning': !complete && task.rankA,
                    'panel-success': !complete && task.rankB,
                    'panel-info': !complete && task.rankC,
                    'panel-default': complete
                },
                
                btnClass: {
                    'btn-danger': !complete && task.rankS,
                    'btn-warning': !complete && task.rankA,
                    'btn-success': !complete && task.rankB,
                    'btn-info': !complete && task.rankC,
                    'btn-default': complete
                },
                
                remove: () => {
                    removeTaskService.remove({
                        id: task.id,
                        title: task.title,
                        onRemove: $scope.onRemove
                    });
                },
                
                switchStatus: (task, status : string, event) => {
                    taskResource.switchStatus({
                        id: task.id,
                        lastUpdateDate: task.lastUpdateDate,
                        status: status
                    }, function onSuccess() {
                        $scope.onSwitchStatus();
                    }, function onNotFoundError() {
                        alert('指定されたタスクは既に削除されています。');
                    });
                    
                    event.preventDefault();
                }
            });
        }
    };
}]);
