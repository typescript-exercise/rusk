module rusk {
    export module view {
        export module primitive {
            export class TextBox {
                private $element;
                private value : ValueAccessor<string>;
                
                constructor($element, value : ValueAccessor<string>) {
                    this.$element = $element;
                    this.value = value;
                }
                
                getValue() : string {
                    return this.value.getValue();
                }
                
                clear() : void {
                    this.value.clear();
                }
                
                focus() : void {
                    this.$element.focus();
                }
            }
        }
    }
}
