module rusk {
    export module resource {
        export module system {
            export class SystemResource {
                private $http : ng.IHttpService;
                
                constructor($http : ng.IHttpService) {
                    this.$http = $http;
                }
                
                initialize() : void {
                    this.$http
                        .post('rest/system', {})
                        .success(() => {
                            console.log('success');
                        });
                }
            }
        }
    }
}

angular
.module('rusk')
.factory('systemResource', [
    '$http',
    ($http) => {
        return new rusk.resource.system.SystemResource($http);
    }
]);
