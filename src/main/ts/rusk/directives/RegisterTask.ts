angular
.module('rusk')
.directive('ruskRegisterTask', [
'$filter',
($filter) => {
    return {
        restrict: 'E',
        replace: true,
        scope: true,
        controller: 'RegisterTaskController',
        templateUrl: 'directives/RegisterTask.html',
        link: ($scope, $element, $attr) => {
            focusTitleWhenShownModal($element);
            
            var modal = createModal($scope, $element, $filter);
            var validationRules = createValidationRules();
            
            setupScope($scope, modal, validationRules);
            
            modal.clearForms();
            
            
            
            function focusTitleWhenShownModal($element) {
                $element.on('shown.bs.modal', function() {
                    $(this).find('#title').focus();
                });
            }
            
            function createModal($scope, $element, $filter) : rusk.view.RegisterTaskModal {
                return {
                    close: () => {
                        $element.modal('hide');
                    },
                    
                    clearForms: () => {
                        $scope.title = '';
                        $scope.importance = 'B';
                        $scope.detail = '';
                        $element.find('#period').val($filter('date')(new Date(), 'yyyy/MM/dd HH:mm'));
                    },
                    
                    getTask: () => {
                        return {
                            title: $scope.title,
                            period: $('#period').val(),
                            importance: $scope.importance,
                            detail: $scope.detail
                        };
                    }
                }
            }
            
            function createValidationRules() {
                var builder = rusk.validation.TaskValidateOptionBuilder.create();
                return builder.title().period().importance().detail().build();
            }
            
            function setupScope($scope, modal : rusk.view.RegisterTaskModal, validationRules) {
                _.extend($scope, {
                    registerModal: modal,
                    
                    validateOption: {
                        rules: validationRules
                    }
                });
            }
        }
    };
}]);

declare module rusk {
    export module view {
        export interface RegisterTaskModal {
            close() : void;
            clearForms(): void;
            getTask() : any;
        }
    }
}
