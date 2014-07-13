angular
.module('rusk')
.directive('ruskTaskTag', () => {
    return {
        restrict: 'E',
        replace: true,
        scope: {
            task: '=task'
        },
        templateUrl: 'directives/TaskTag.html',
        link: ($scope, $element, $attr) => {
            var task = $scope.task;
            var complete = task.status === 'COMPLETE';
            
            $scope.panelClass = {
                'panel-danger': !complete && task.rankS,
                'panel-warning': !complete && task.rankA,
                'panel-success': !complete && task.rankB,
                'panel-info': !complete && task.rankC,
                'panel-default': complete
            };
            
            $scope.labelClass = {
                'label-danger': !complete && task.rankS,
                'label-warning': !complete && task.rankA,
                'label-success': !complete && task.rankB,
                'label-info': !complete && task.rankC,
                'label-default': complete
            };
        }
    };
});
