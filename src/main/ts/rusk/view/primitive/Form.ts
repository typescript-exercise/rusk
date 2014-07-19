module rusk {
    export module view {
        export module primitive {
            export class Form {
                private $element;
                
                constructor($element, validationRules) {
                    this.$element = $element;
                    
                    this.$element.validate(validationRules);
                }
            }
        }
    }
}
