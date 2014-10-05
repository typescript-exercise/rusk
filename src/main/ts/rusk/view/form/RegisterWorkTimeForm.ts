/// <reference path="../primitive/DateTime.ts" />
/// <reference path="../primitive/Form.ts" />
/// <reference path="./TaskValidateOptionBuilder.ts" />

module rusk {
    export module view {
        export module form {
            import DateTime = primitive.DateTime;
            import Form = primitive.Form;
            
            export interface RegisterWorkTimeFormConstructParameter {
                startTime: {
                    selector: string;
                };
                endTime: {
                    selector: string;
                };
                form: {
                    selector: string;
                };
            }
            
            export class RegisterWorkTimeForm {
                private startTime : DateTime;
                private endTime : DateTime;
                private form : Form;
                
                constructor(param : RegisterWorkTimeFormConstructParameter) {
                    this.startTime = new DateTime($(param.startTime.selector), {minDate: '2014-01-01'});
                    this.endTime = new DateTime($(param.endTime.selector), {minDate: '2014-01-01'});
                    this.initForm(param);
                }
                
                private initForm(param : RegisterWorkTimeFormConstructParameter) {
                    var $element = $(param.form.selector);
                    
                    this.form = new Form($element, TaskValidateOptionBuilder.create().startTime().endTime().custom({
                        name: 'endTime',
                        method: 'test',
                        validation: (value, element, param) : boolean => {
                            console.dir({
                                value: value,
                                element: element,
                                param: param
                            });
                            return false;
                        },
                        message: 'test desu'
                    }).build());
                }
                
                isValid() : boolean {
                    return this.form.isValid();
                }
                
                getParams() {
                    return {
                        startTime: this.startTime.getValue(),
                        endTime: this.endTime.getValue()
                    };
                }
            }
        }
    }
}
