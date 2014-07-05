/// <reference path="rusk/Sample.ts"/>

import Sample = rusk.Sample;

angular
.module('rusk', [])
.run(['$rootScope', (scope) => {
    
    var sample = new Sample('AngularJS with TypeScript.');
    
    scope.message = sample.getName();
    
}]);