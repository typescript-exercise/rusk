module rusk {
    export module view {
        export module form {
            export class TaskDetailForm {
                private title : primitive.TextBox;
                private status : primitive.SelectBox;
                private period : primitive.DateTime;
                private importance : primitive.SelectBox;
                private detail : primitive.TextArea;
                private form : primitive.Form;
                
                constructor(formElements) {
                    this.title = formElements.title;
                    this.status = formElements.status;
                    this.period = formElements.period;
                    this.importance = formElements.importance;
                    this.detail = formElements.detail;
                    this.form = formElements.form;
                }
            }
        }
    }
}