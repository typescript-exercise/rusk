module rusk {
    export module view {
        export module primitive {
            export class DateTime {
                private $element;
                
                constructor($element) {
                    this.$element = $element;
                    
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
                        minDate: 0, // today
                        value: rusk.formatDate(new Date(), 'yyyy/MM/dd HH:mm')
                    });
                }
                
                getValue() : string {
                    var date = new Date(this.$element.val());
                    return rusk.formatDate(date, "yyyy-MM-dd'T'HH:mm:00.000+0900");
                }
                
                reset() : void {
                    this.$element.val(rusk.formatDate(new Date(), 'yyyy/MM/dd HH:mm'));
                }
            }
        }
    }
}
