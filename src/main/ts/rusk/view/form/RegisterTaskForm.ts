module rusk {
    export module view {
        export module form {
            export interface RegisterTaskFormConstructParameter {
                baseEelement: any;
                scope: any;
                title: {
                    selector: string;
                    name: string;
                };
                period: {
                    selector: string;
                };
                importance: {
                    name: string;
                };
                detail: {
                    selector: string;
                    name: string;
                };
                form: {
                    selector: string;
                }
            }
            
            export class RegisterTaskForm {
                private title : primitive.TextBox;
                private period : primitive.DateTime;
                private importance : primitive.SelectBox;
                private detail : primitive.TextArea;
                private form : primitive.Form;
                
                constructor(param : RegisterTaskFormConstructParameter) {
                    this.initTitle(param);
                    this.initPeriod(param);
                    this.initImportance(param);
                    this.initDetail(param);
                    this.initForm(param);
                }
                
                private initTitle(param : RegisterTaskFormConstructParameter) {
                    var $element = param.baseEelement.find(param.title.selector);
                    var accessor = this.createAccessor(param, param.title.name);
                    this.title = new primitive.TextBox($element, accessor);
                }
                
                private initPeriod(param : RegisterTaskFormConstructParameter) {
                    var $element = param.baseEelement.find(param.period.selector);
                    this.period = new primitive.DateTime($element);
                }
                
                private initImportance(param : RegisterTaskFormConstructParameter) {
                    var accessor = this.createAccessor(param, param.importance.name);
                    this.importance = new primitive.SelectBox(accessor);
                }
                
                private initDetail(param : RegisterTaskFormConstructParameter) {
                    var $element = param.baseEelement.find(param.detail.selector);
                    var accessor = this.createAccessor(param, param.detail.name);
                    this.detail = new primitive.TextArea($element, accessor);
                }
                
                private initForm(param : RegisterTaskFormConstructParameter) {
                    var $element = param.baseEelement.find(param.form.selector);
                    var validateOptions = TaskValidateOptionBuilder.create().title().period().importance().detail().build();
                    this.form = new primitive.Form($element, validateOptions);
                }
                
                private createAccessor(param, name) : primitive.AngularScopeValue {
                    return new primitive.AngularScopeValue(param.scope, name);
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
