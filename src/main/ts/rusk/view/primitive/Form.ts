module rusk {
    export module view {
        export module primitive {
            export class Form {
                private $element;
                private validator;
                
                constructor($element, validationRules) {
                    this.$element = $element;
                    this.validator = this.$element.validate(_.extend({}, validationRules, {onfocusout: false, focusInvalid : false}));
                }
                
                isValid() : boolean {
                    return this.$element.valid();
                }
                
                reset() : void {
                console.log('reset');
                    this.$element.valid();
                }
            }
        }
    }
}
