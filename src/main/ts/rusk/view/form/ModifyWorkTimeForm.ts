/// <reference path="../primitive/DateTime.ts" />
/// <reference path="../primitive/Form.ts" />
/// <reference path="./TaskValidateOptionBuilder.ts" />

module rusk {
    export module view {
        export module form {
            import DateTime = primitive.DateTime;
            import Form = primitive.Form;
            
            export interface ModifyWorkTimeFormConstructParameter {
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
            
            export class ModifyWorkTimeForm {
                private startTime : DateTime;
                private endTime : DateTime;
                private form : Form;
                
                constructor(param : ModifyWorkTimeFormConstructParameter) {
                    this.startTime = new DateTime($(param.startTime.selector), {minDate: '2014-01-01', normal: true});
                    this.endTime = new DateTime($(param.endTime.selector), {minDate: '2014-01-01', normal: true});
                    this.initForm(param);
                }
                
                private initForm(param : ModifyWorkTimeFormConstructParameter) {
                    var $element = $(param.form.selector);
                    
                    this.form = new Form($element, TaskValidateOptionBuilder.create().startTime().endTime(this.startTime, this.endTime).build());
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
                
                setStartTime(dateByMillisec : number) : void {
                    this.startTime.setValue(new Date(dateByMillisec));
                }
                
                setEndTime(dateByMillisec : number) : void {
                    if (_.isNull(dateByMillisec)) { // 作業中の場合は null
                        this.endTime.clear();
                    } else {
                        this.endTime.setValue(new Date(dateByMillisec));
                    }
                }
                
                getStartTime() : string {
                    return this.startTime.getValue();
                }
                
                getEndTime() : string {
                    return this.endTime.getValue();
                }
                
                resetValidation() : void {
                    this.form.reset();
                }
                
                clearErrorStyle() : void {
                    this.startTime.clearErrorStyle();
                    this.endTime.clearErrorStyle();
                }
            }
        }
    }
}
