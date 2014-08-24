/// <reference path="./rusk/model/models.ts" />

angular
.module('rusk', ['ngResource', 'ngRoute', 'ui.utils'])
.config([
    '$routeProvider', '$httpProvider',
    ($routeProvider, $httpProvider : ng.IHttpProvider) => {
        $routeProvider
        .when('/', {
            controller: 'TaskListController',
            templateUrl: 'views/task-list.html'
        })
        .when('/task/:id', {
            controller: 'TaskDetailController',
            templateUrl: 'views/task-detail.html'
        })
        .when('/task/:id/work-times', {
            controller: 'WorkTimeController',
            templateUrl: 'views/work-times.html'
        })
        .when('/server-error', {
            templateUrl: 'views/server-error.html'
        })
        .when('/bad-request', {
            templateUrl: 'views/bad-request.html'
        })
        .when('/not-found', {
            templateUrl: 'views/not-found.html'
        });
        
        $httpProvider.interceptors.push('HttpResponseInterceptor');
    }])
.run(['$rootScope', '$location', '$route',
    ($rootScope, $location, $route) => {
        var validator = (<any>$).validator;
        
        _.extend(validator.messages, {
            required: 'この項目は必須入力です。',
            maxlength: validator.format('この項目は {0} 文字以下で入力してください。')
        });
        
        $rootScope.moveToTopPage = () => {
            $location.path('/');
            $route.reload(); // 現在のページが "/" の場合、これをしないと再描画してくれない
        };
    }]);
