module rusk {
    export module view {
        export module primitive {
            export class DateTime {
                private $element;
                
                constructor($element, options?) {
                    this.$element = $element;
                    
                    var defaultDate = options ? new Date(options.defaultDate) : new Date();
                    var minDate = options ? new Date(options.minDate) : new Date();
                    
                    var enableDefaultDate = new rusk.model.FixdIntervalDate(defaultDate);
                    var enableMinDate = new rusk.model.FixdIntervalDate(minDate);
                    
                    this.$element.datetimepicker({
                        mask: true,
                        lang: 'ja',
                        i18n: {
                            ja: {
                                months: [
                                    '1月', '2月', '3月', '4月', '5月', '6月',
                                    '7月', '8月', '9月', '10月', '11月', '12月'
                                ],
                                dayOfWeek: [
                                    '日', '月', '火', '水', '木', '金', '土'
                                ]
                            }
                        },
                        step: 30,
                        minDate: enableMinDate.getDateAsString(),
                        value: rusk.formatDate(enableDefaultDate.getDate(), 'yyyy/MM/dd HH:mm')
                    });
                }
                
                getValue() : string {
                    var date = new Date(this.$element.val());
                    return rusk.formatDate(date, "yyyy-MM-dd'T'HH:mm:00.000+0900"); // Jackson がサポートしているフォーマットに合わせる
                }
                
                setValue(date : Date) : void {
                    var enableDate = new rusk.model.FixdIntervalDate(date);
                    this.$element.val(rusk.formatDate(enableDate.getDate(), 'yyyy/MM/dd HH:mm'));
                }
            }
        }
    }
}
