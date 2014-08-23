/// <reference path="../primitive/TextBox.ts" />
/// <reference path="../primitive/DateTime.ts" />
/// <reference path="../primitive/SelectBox.ts" />
/// <reference path="../primitive/TextArea.ts" />
/// <reference path="../primitive/Form.ts" />
/// <reference path="../primitive/AngularScopeValue.ts" />

module rusk {
    export module view {
        export module form {
            import TextBox = primitive.TextBox;
            import DateTime = primitive.DateTime;
            import SelectBox = primitive.SelectBox;
            import TextArea = primitive.TextArea;
            import Form = primitive.Form;
            import AngularScopeValue = primitive.AngularScopeValue;
            
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
                private title : TextBox;
                private status : SelectBox;
                private period : DateTime;
                private importance : SelectBox;
                private detail : TextArea;
                private form : Form;
                
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
                    this.title = new TextBox($element, accessor);
                }
                
                private initStatus(param : TaskDetailFormConstructParameter) {
                    var accessor = this.createAccessor(param.task, param.status.name);
                    this.status = new SelectBox(accessor);
                }
                
                private initPeriod(param : TaskDetailFormConstructParameter) {
                    var $element = $(param.period.selector);
                    this.period = new DateTime($element, param.period.options);
                }
                
                private initImportance(param : TaskDetailFormConstructParameter) {
                    var accessor = this.createAccessor(param.task.priority, param.importance.name);
                    this.importance = new SelectBox(accessor);
                }
                
                private initDetail(param : TaskDetailFormConstructParameter) {
                    var $element = $(param.detail.selector);
                    var accessor = this.createAccessor(param.task, param.detail.name);
                    this.detail = new TextArea($element, accessor);
                }
                
                private initForm(param : TaskDetailFormConstructParameter) {
                    var $element = $(param.form.selector);
                    var validateOptions = TaskValidateOptionBuilder.create().title().period().importance().detail().build();
                    this.form = new Form($element, validateOptions);
                }
                
                private createAccessor(scope, name) : AngularScopeValue {
                    return new AngularScopeValue(scope, name)
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
