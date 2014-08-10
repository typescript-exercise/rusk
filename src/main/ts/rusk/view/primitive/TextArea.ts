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
                    
                    this.initializeAutosize();
                }
                
                private initializeAutosize() : void {
                    // AngularJS のタイミング的に、この時点では値が設定されておらず、テキストエリアのサイズが調整できない
                    // なので、この場で強制的に値を設定している。
                    this.$element.val(this.$scope[this.name]);
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
