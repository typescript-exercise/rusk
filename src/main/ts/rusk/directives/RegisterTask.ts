angular
.module('rusk')
.directive('ruskRegisterTask', () => {
    return {
        restrict: 'E',
        replace: true,
        scope: true,
        controller: 'RegisterTaskController',
        templateUrl: 'directives/RegisterTask.html',
        link: ($scope, $element, $attr) => {
            $element.on('shown.bs.modal', function() {
                $(this).find('#title').focus();
            });
            
            $scope.closeModal = () => {
                $element.modal('hide');
            };
        }
    };
});
