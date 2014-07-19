module rusk {
    export module view {
        export module form {
            export class RegisterTaskForm {
                private title : primitive.TextBox;
                private period : primitive.DateTime;
                private importance : primitive.SelectBox;
                private detail : primitive.TextArea;
                private $element;
                
                constructor($scope, $element, selectors) {
                    this.title = new primitive.TextBox($scope, $element.find(selectors.title), 'title');
                    this.period = new primitive.DateTime($element.find(selectors.period));
                    this.importance = new primitive.SelectBox($scope, 'importance');
                    this.detail = new primitive.TextArea($scope, $element.find(selectors.detail), 'detail');
                    
                    this.$element = $element;
                    
                    this.setValidationRules();
                }
                
                private setValidationRules() {
                    var builder = rusk.config.validation.TaskValidateOptionBuilder.create();
                    var options = builder.title().period().importance().detail().build();
                    
                    this.$element.validate(options);
                }
                
                getTask() {
                    return {
                        title: this.title.getValue(),
                        period: this.period.getValue(),
                        importance: this.importance.getValue(),
                        detail: this.detail.getValue()
                    };
                }
                
                reset() {
                    this.title.clear();
                    this.period.reset();
                    this.importance.setValue('B');
                    this.detail.clear();
                }
            }
        }
    }
}
