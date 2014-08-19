module rusk {
    export module view {
        export module form {
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
                
                build() : any {
                    return this.option;
                }
            }
        }
    }
}
