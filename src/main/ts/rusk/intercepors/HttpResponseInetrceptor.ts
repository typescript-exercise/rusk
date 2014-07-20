angular
.module('rusk')
.factory('HttpResponseInterceptor',[
    '$q', '$location',
    ($q : ng.IQService, $location : ng.ILocationService) => {
        return {
            responseError: function(response) {
                switch (response.status) {
                case 400:
                    $location.path('/bad-request').replace();
                    break;
                case 404:
                    $location.path('/not-found').replace();
                    break;
                case 500:
                    $location.path('/server-error').replace();
                    break;
                }
    
                return $q.reject(response);
            }
        };
    }]);