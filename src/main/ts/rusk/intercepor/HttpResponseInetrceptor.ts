/// <reference path="./ErrorHandler.ts" />

module ruks {
    import ErrorHandlerFactory = rusk.interceptor.ErrorHandlerFactory;
    import ErrorHandler = rusk.interceptor.ErrorHandler;
    import RedirectErrorHandler = rusk.interceptor.RedirectErrorHandler;
    import AlertErrorHandler = rusk.interceptor.AlertErrorHandler;
    
    angular
    .module('rusk')
    .factory('HttpResponseInterceptor',[
        '$q', '$location', 'ErrorHandlerFactory',
        ($q : ng.IQService, $location : ng.ILocationService, ErrorHandlerFactory : ErrorHandlerFactory) => {
            return {
                responseError: function(response) {
                    var errorHandler = ErrorHandlerFactory.createErrorHandler(response.status, response.config);
                    
                    errorHandler.handle();
                    
                    return $q.reject(response);
                }
            };
        }])
    .factory('ErrorHandlerFactory', [
        '$location',
        ($location) => {
            return {
                createErrorHandler: (status : number, config : any) : ErrorHandler => {
                    if (isDefinedOrverrideHandler(status, config)) {
                        return createOrverrideHandler(status, config);
                    } else {
                        return createOriginalErrorHandler(status);
                    }
                }
            };
            
            function isDefinedOrverrideHandler(status : number, config) : boolean {
                if (config && config.overrideInterceptor) {
                    return _.isFunction(config.overrideInterceptor[status]);
                } else {
                    return false;
                }
            }
            
            function createOrverrideHandler(status : number, config) : ErrorHandler {
                return {
                    handle: config.overrideInterceptor[status]
                };
            }
            
            function createOriginalErrorHandler(status : number) : ErrorHandler {
                switch (status) {
                case 400:
                    return new RedirectErrorHandler($location, '/bad-request');
                    break;
                case 404:
                    return new RedirectErrorHandler($location, '/not-found');
                    break;
                case 409:
                    return new AlertErrorHandler('同時更新されています。\n画面を更新して、もう一度試してください。');
                    break;
                case 500:
                    return new RedirectErrorHandler($location, '/server-error');
                    break;
                default:
                    return  { 
                        handle: () : void => {}
                    }; 
                }
            }
        }]);
}
