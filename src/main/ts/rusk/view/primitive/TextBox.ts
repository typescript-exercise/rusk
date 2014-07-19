module rusk {
    export module view {
        export module primitive {
            export class TextBox {
                private $element;
                private $scope;
                private name : string;
                
                constructor($scope, $element, name : string) {
                    this.$element = $element;
                    this.$scope = $scope;
                    this.name = name;
                }
                
                getValue() : string {
                    return this.$scope[this.name];
                }
                
                clear() : void {
                    this.$scope[this.name] = '';
                }
                
                focus() : void {
                    this.$element.focus();
                }
            }
        }
    }
}
