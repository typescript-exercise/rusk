module rusk {
    export module view {
        export module form {
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
                                required: true
                            }
                        }
                    });
                    return this;
                }
                
                endTime() : TaskValidateOptionBuilder {
                    $.extend(true, this.option, {
                        rules: {
                            endTime: {
                                required: true
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
        }
    }
}
