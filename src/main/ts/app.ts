angular
.module('rusk', ['ngResource', 'ngRoute'])
.run([
    '$rootScope', 'systemService',
    ($rootScope,   systemService : rusk.resource.system.SystemResource) => {
        systemService.initialize();
    }])
.config([
    '$routeProvider',
    ($routeProvider) => {
        $routeProvider
        .when('/', {
            controller: 'TaskListController',
            templateUrl: 'views/task-list.html'
        });
    }]);