module rusk {
    export module view {
        export module form {
            
            export interface TaskDetailFormConstructParameter {
                task: any;
                title: {
                    selector: string;
                    name: string;
                }
                status: {
                    name: string;
                }
                period: {
                    selector: string;
                    options: {
                        defaultDate: any;
                        minDate: any;
                    }
                }
                importance: {
                    name: string;
                }
                detail: {
                    selector: string;
                    name: string;
                }
                form: {
                    selector: string;
                }
            }
            
            export class TaskDetailForm {
                private title : primitive.TextBox;
                private status : primitive.SelectBox;
                private period : primitive.DateTime;
                private importance : primitive.SelectBox;
                private detail : primitive.TextArea;
                private form : primitive.Form;
                
                constructor(param : TaskDetailFormConstructParameter) {
                    this.initTitle(param);
                    this.initStatus(param);
                    this.initPeriod(param);
                    this.initImportance(param);
                    this.initDetail(param);
                    this.initForm(param);
                }
                
                private initTitle(param : TaskDetailFormConstructParameter) {
                    var $element = $(param.title.selector);
                    var accessor = this.createAccessor(param.task, param.title.name);
                    this.title = new primitive.TextBox($element, accessor);
                }
                
                private initStatus(param : TaskDetailFormConstructParameter) {
                    var accessor = this.createAccessor(param.task, param.status.name);
                    this.status = new primitive.SelectBox(accessor);
                }
                
                private initPeriod(param : TaskDetailFormConstructParameter) {
                    var $element = $(param.period.selector);
                    this.period = new primitive.DateTime($element, param.period.options);
                }
                
                private initImportance(param : TaskDetailFormConstructParameter) {
                    var accessor = this.createAccessor(param.task.priority, param.importance.name);
                    this.importance = new primitive.SelectBox(accessor);
                }
                
                private initDetail(param : TaskDetailFormConstructParameter) {
                    var $element = $(param.detail.selector);
                    var accessor = this.createAccessor(param.task, param.detail.name);
                    this.detail = new primitive.TextArea($element, accessor);
                }
                
                private initForm(param : TaskDetailFormConstructParameter) {
                    var $element = $(param.form.selector);
                    var validateOptions = TaskValidateOptionBuilder.create().title().period().importance().detail().build();
                    this.form = new primitive.Form($element, validateOptions);
                }
                
                private createAccessor(scope, name) : primitive.AngularScopeValue {
                    return new primitive.AngularScopeValue(scope, name)
                }
                
                isValid() : boolean {
                    return this.form.isValid();
                }
                
                getParams() {
                    return {
                        title: this.title.getValue(),
                        detail: this.detail.getValue(),
                        status: this.status.getValue(),
                        period: this.period.getValue(),
                        importance: this.importance.getValue()
                    };
                }
            }
        }
    }
}
