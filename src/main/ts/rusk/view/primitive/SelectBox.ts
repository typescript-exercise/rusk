module rusk {
    export module view {
        export module primitive {
            export class SelectBox {
                private value : ValueAccessor<string>;
                
                constructor(value : ValueAccessor<string>) {
                    this.value = value;
                }
                
                setValue(value : string) : void {
                    this.value.setValue(value);
                }
                
                getValue() : string {
                    return this.value.getValue();
                }
            }
        }
    }
}
