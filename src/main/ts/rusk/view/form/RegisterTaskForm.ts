module rusk {
    export module view {
        export module form {
            export class RegisterTaskForm {
                private title : primitive.TextBox;
                private period : primitive.DateTime;
                private importance : primitive.SelectBox;
                private detail : primitive.TextArea;
                private form : primitive.Form;
                
                constructor(formElements) {
                    this.title = formElements.title;
                    this.period = formElements.period;
                    this.importance = formElements.importance;
                    this.detail = formElements.detail;
                    this.form = formElements.form;
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
                    this.period.setValue(new Date());
                    this.importance.setValue('B');
                    this.detail.clear();
                }
                
                isValid() : boolean {
                    return this.form.isValid();
                }
            }
        }
    }
}
