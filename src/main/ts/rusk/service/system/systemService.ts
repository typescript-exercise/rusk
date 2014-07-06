angular
.module('rusk')
.service('systemService',[
         '$http',
function($http : ng.IHttpService) {
    this.initialize = () => {
        $http
            .post('rest/system', {})
            .success(() => {
                console.log('success');
            });
    };
}]);

