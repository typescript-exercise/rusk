/// <reference path="./rusk/model/models.ts" />

angular
.module('rusk', ['ngResource', 'ngRoute', 'ui.utils'])
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
    }])
.run(() => {
    var validator = (<any>$).validator;
    
    _.extend(validator.messages, {
        required: 'この項目は必須入力です。',
        maxlength: validator.format('この項目は {0} 文字以下で入力してください。')
    });
})
.value('uiJqConfig', {
    datetimepicker: {
        mask: true,
        lang: 'ja',
        step: 30,
        minDate: 0, // today
        i18n: {
            ja: {
                months: [
                    '1月', '2月', '3月', '4月', '5月', '6月',
                    '7月', '8月', '9月', '10月', '11月', '12月'
                ],
                dayOfWeek: [
                    '日', '月', '火', '水', '木', '金', '土'
                ]
            }
        }
    },
    
    validate: {
    }
});
