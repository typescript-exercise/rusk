module rusk {
    export module view {
        export module primitive {
            export class TextArea {
                private $element;
                private value : ValueAccessor<string>;
                
                constructor($element, value : ValueAccessor<string>) {
                    this.$element = $element;
                    this.value = value;
                    this.initializeAutosize();
                }
                
                private initializeAutosize() : void {
                    // AngularJS のタイミング的に、この時点では値が設定されておらず、テキストエリアのサイズが調整できない
                    // なので、この場で強制的に値を設定している。
                    this.$element.val(this.value.getValue());
                    this.$element.autosize();
                }
                
                setValue(value : string) : void {
                    this.value.setValue(value);
                }
                
                getValue() : string {
                    return this.value.getValue();
                }
                
                clear() : void {
                    this.value.clear();
                }
            }
        }
    }
}
