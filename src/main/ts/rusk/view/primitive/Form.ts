module rusk {
    export module view {
        export module primitive {
            export class Form {
                private $element;
                
                constructor($element, validationRules) {
                    this.$element = $element;
                    this.$element.validate(_.extend({}, validationRules, {onfocusout: false, focusInvalid : false}));
                }
                
                isValid() : boolean {
                    return this.$element.valid();
                }
            }
        }
    }
}
