module rusk {
    export module interceptor {
        export interface ErrorHandlerFactory {
            createErrorHandler(status : number, config : any) : ErrorHandler;
        }
        
        export interface ErrorHandler {
            handle(response : any) : void;
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
        
        export class AlertErrorHandler implements ErrorHandler {
            private message : string;
            
            constructor(message : string) {
                this.message = message;
            }
            
            handle() : void {
                alert(this.message);
            }
        }
    }
}
