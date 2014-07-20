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
.run(() => {
    var validator = (<any>$).validator;
    
    _.extend(validator.messages, {
        required: 'この項目は必須入力です。',
        maxlength: validator.format('この項目は {0} 文字以下で入力してください。')
    });
});
