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
                private title : TextBox;
                private period : DateTime;
                private importance : SelectBox;
                private detail : TextArea;
                private form : Form;
                
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
                    this.title = new TextBox($element, accessor);
                }
                
                private initPeriod(param : RegisterTaskFormConstructParameter) {
                    var $element = param.baseEelement.find(param.period.selector);
                    this.period = new DateTime($element);
                }
                
                private initImportance(param : RegisterTaskFormConstructParameter) {
                    var accessor = this.createAccessor(param, param.importance.name);
                    this.importance = new SelectBox(accessor);
                }
                
                private initDetail(param : RegisterTaskFormConstructParameter) {
                    var $element = param.baseEelement.find(param.detail.selector);
                    var accessor = this.createAccessor(param, param.detail.name);
                    this.detail = new TextArea($element, accessor);
                }
                
                private initForm(param : RegisterTaskFormConstructParameter) {
                    var $element = param.baseEelement.find(param.form.selector);
                    var validateOptions = TaskValidateOptionBuilder.create().title().period().importance().detail().build();
                    this.form = new Form($element, validateOptions);
                }
                
                private createAccessor(param, name) : AngularScopeValue {
                    return new AngularScopeValue(param.scope, name);
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
