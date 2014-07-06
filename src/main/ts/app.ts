/// <reference path="app.d.ts" />

angular
.module('rusk', ['ngResource'])
.run([
'$rootScope', 'systemService',
($rootScope,   systemService : rusk.service.system.SystemService) => {
    systemService.initialize();
}]);
