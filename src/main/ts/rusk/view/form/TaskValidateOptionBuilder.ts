/// <reference path="../primitive/DateTime.ts" />

module rusk {
    export module view {
        export module form {
            import DateTime = primitive.DateTime;
            
            export interface CutsomValidation {
                name: string;
                method: string;
                validation: (value, element, param) => boolean;
                message: string;
            }
            
            export class TaskValidateOptionBuilder {
                private option = {};
                
                static create() : TaskValidateOptionBuilder {
                    return new TaskValidateOptionBuilder();
                }
                
                title() : TaskValidateOptionBuilder {
                    $.extend(true, this.option, {
                        rules: {
                            title: {
                                required: true,
                                maxlength: 50
                            }
                        }
                    });
                    return this;
                }
                
                period() : TaskValidateOptionBuilder {
                    $.extend(true, this.option, {
                        rules: {
                            period: {
                                required: true
                            }
                        }
                    });
                    return this;
                }
                
                startTime() : TaskValidateOptionBuilder {
                    $.extend(true, this.option, {
                        rules: {
                            startTime: {
                                requireDateTime: true
                            }
                        }
                    });
                    return this;
                }
                
                endTime(startTime : DateTime, endTime : DateTime) : TaskValidateOptionBuilder {
                    $.extend(true, this.option, {
                        rules: {
                            endTime: {
                                requireDateTime: true,
                                endTimeCustom: {
                                    startTime: startTime,
                                    endTime: endTime
                                }
                            }
                        }
                    });
                    
                    return this;
                }
                
                importance() : TaskValidateOptionBuilder {
                    $.extend(true, this.option, {
                        rules: {
                            importance: {
                                required: true
                            }
                        }
                    });
                    return this;
                }
                
                detail() : TaskValidateOptionBuilder {
                    $.extend(true, this.option, {
                        rules: {
                            detail: {
                                maxlength: 1000
                            }
                        }
                    });
                    return this;
                }
                
                custom(option : CutsomValidation): TaskValidateOptionBuilder {
                    (<any>$).validator.addMethod(option.method, option.validation, option.message);
                    
                    var _option : any = this.option;
                    
                    if (_.isUndefined(_option.rules)) {
                        _option.rules = {};
                    }
                    
                    if (_.isUndefined(_option.rules[option.name])) {
                        _option.rules[option.name] = {};
                    }
                    
                    _option.rules[option.name][option.method] = true;
                    
                    console.dir(this.option);
                    
                    return this;
                }
                
                build() : any {
                    return this.option;
                }
            }
            
            (function() {
                var validator = (<any>$).validator;
                
                validator.addMethod(
                    'endTimeCustom',
                    (value, element, param) => {
                        var startTime : DateTime = param.startTime;
                        var endTime : DateTime = param.endTime;
                        
                        return startTime.lessThan(endTime);
                    },
                    '開始日時＜終了日時となるように入力してください。'
                );
                
                validator.addMethod(
                    'requireDateTime',
                    (value, element, param) => {
                        return !DateTime.isEmpty(value);
                    },
                    'この項目は必須入力です。'
                );
            })();
        }
    }
}
