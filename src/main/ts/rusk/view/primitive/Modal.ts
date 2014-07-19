module rusk {
    export module view {
        export module primitive {
            export class Modal {
                private $element;
                
                constructor($element) {
                    this.$element = $element;
                }
                
                onShown(callback : Function) : void {
                    this.$element.on('shown.bs.modal', callback);
                }
                
                close() : void {
                    this.$element.modal('hide');
                }
            }
        }
    }
}
