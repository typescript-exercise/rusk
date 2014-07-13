/// <reference path="./rusk/model/models.ts" />

angular
.module('rusk', ['ngResource', 'ngRoute'])
.config([
    '$routeProvider',
    ($routeProvider) => {
        $routeProvider
        .when('/', {
            controller: 'TaskListController',
            templateUrl: 'views/task-list.html'
        })
        .when('/server-error', {
            templateUrl: 'views/server-error.html'
        });
    }]);
