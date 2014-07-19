module rusk {
    export module view {
        export module primitive {
            export class TextArea {
                private $element;
                private $scope;
                private name : string;
                
                constructor($scope, $element, name : string) {
                    this.$element = $element;
                    this.$scope = $scope;
                    this.name = name;
                    
                    this.$element.autosize();
                }
                
                setValue(value : string) : void {
                    this.$scope[this.name] = value;
                }
                
                getValue() : string {
                    return this.$scope[this.name];
                }
                
                clear() : void {
                    this.$scope[this.name] = '';
                }
            }
        }
    }
}
