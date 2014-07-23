angular
.module('rusk')
.factory('HttpResponseInterceptor',[
    '$q', '$location', 'ErrorHandlerFactory',
    ($q : ng.IQService, $location : ng.ILocationService, ErrorHandlerFactory : rusk.interceptors.ErrorHandlerFactory) => {
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
            createErrorHandler: (status : number, config : any) : rusk.interceptors.ErrorHandler => {
                if (isDefinedOrverrideHandler(status, config)) {
                    return createOrverrideHandler(status, config);
                } else {
                    return createOriginalErrorHandler(status);
                }
            }
        };
        
        function isDefinedOrverrideHandler(status : number, config) : boolean {
            if (config) {
                return _.isFunction(config.overrideInterceptor[status]);
            } else {
                return false;
            }
        }
        
        function createOrverrideHandler(status : number, config) : rusk.interceptors.ErrorHandler {
            return {
                handle: config.overrideInterceptor[status]
            };
        }
        
        function createOriginalErrorHandler(status : number) : rusk.interceptors.ErrorHandler {
            switch (status) {
            case 400:
                return new rusk.interceptors.RedirectErrorHandler($location, '/bad-request');
                break;
            case 404:
                return new rusk.interceptors.RedirectErrorHandler($location, '/not-found');
                break;
            case 500:
                return new rusk.interceptors.RedirectErrorHandler($location, '/server-error');
                break;
            default:
                return  { 
                    handle: () : void => {}
                }; 
            }
        }
    }]);

module rusk {
    export module interceptors {
        export interface ErrorHandlerFactory {
            createErrorHandler(status : number, config : any) : ErrorHandler;
        }
        
        export interface ErrorHandler {
            handle() : void;
        }
        
        export class RedirectErrorHandler implements ErrorHandler {
            
            private $location : ng.ILocationService;
            private url : string;
            
            constructor($location : ng.ILocationService, url : string) {
                this.$location = $location;
                this.url = url;
            }
            
            handle() : void {
                this.$location.path(this.url).replace();
            }
        }
    }
}