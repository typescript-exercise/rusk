angular
.module('rusk')
.directive('ruskRankedLabel', [
() => {
    return {
        restrict: 'E',
        transclude: true,
        scope: {
            rank: '=',
            complete: '='
        },
        templateUrl: 'directives/RankedLabel.html',
        link: ($scope, $element, $attr) => {
            var isRankS = $scope.rank === 'S';
            var isRankA = $scope.rank === 'A';
            var isRankB = $scope.rank === 'B';
            var isRankC = $scope.rank === 'C';
            var complete = $scope.complete;
            
            $scope.labelClass = {
                'label-danger': !complete && isRankS,
                'label-warning': !complete && isRankA,
                'label-success': !complete && isRankB,
                'label-info': !complete && isRankC,
                'label-default': complete
            };
        }
    };
}]);
