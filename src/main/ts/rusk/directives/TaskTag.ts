angular
.module('rusk')
.directive('ruskTaskTag', () => {
    return {
        restrict: 'E',
        replace: true,
        scope: {
            task: '=',
            remove: '&'
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
                }
            });
        }
    };
});
