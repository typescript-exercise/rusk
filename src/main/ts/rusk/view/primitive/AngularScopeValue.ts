module rusk {
    export module view {
        export module primitive {
            export class AngularScopeValue implements ValueAccessor<string> {
                private $scope;
                private name : string;
                
                constructor($scope, name : string) {
                    this.$scope = $scope;
                    this.name = name;
                }
                
                setValue(value : string) : void {
                    this.$scope[this.name] = value;
                }
                
                getValue() : string {
                    return this.$scope[this.name];
                }
                
                clear() : void {
                    this.setValue('');
                }
            }
        }
    }
}
