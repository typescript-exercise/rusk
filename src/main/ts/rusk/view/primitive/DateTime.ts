/// <reference path="../../model/FixdIntervalDate.ts" />
/// <reference path="../../model/NormalDate.ts" />

module rusk {
    export module view {
        export module primitive {
            import FixdIntervalDate = rusk.model.FixdIntervalDate;
            import NormalDate = rusk.model.NormalDate;
            
            export class DateTime {
                private static EMPTY_STRING : string = '____/__/__ __:__';
                private $element;
                private normalDate : boolean;
                
                static isEmpty(str : string) : boolean {
                    return str === DateTime.EMPTY_STRING;
                }
                
                constructor($element, options?) {
                    this.$element = $element;
                    this.normalDate = options && options.normal;
                    
                    var defaultDate = options ? new Date(options.defaultDate) : new Date();
                    var minDate = options ? new Date(options.minDate) : new Date();
                    
                    var enableDefaultDate = this.createDate(defaultDate);
                    var enableMinDate = this.createDate(minDate);
                    
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
                
                private createDate(date : Date) {
                    if (this.normalDate) {
                        return new NormalDate(date);
                    } else {
                        return new FixdIntervalDate(date);
                    }
                }
                
                getValue() : string {
                    if (this.isEmpty()) {
                        return null;
                    }
                    
                    var date = new Date(this.$element.val());
                    return rusk.formatDate(date, "yyyy-MM-dd'T'HH:mm:00.000+0900"); // Jackson がサポートしているフォーマットに合わせる
                }
                
                setValue(date : Date) : void {
                    var enableDate = this.createDate(date);
                    this.$element.val(rusk.formatDate(enableDate.getDate(), 'yyyy/MM/dd HH:mm'));
                }
                
                isEmpty() : boolean {
                    return DateTime.isEmpty(this.$element.val());
                }
                
                lessThan(other : DateTime) : boolean {
                    return this.getTime() < other.getTime();
                }
                
                clear() : void {
                    this.$element.val(DateTime.EMPTY_STRING);
                }
                
                private getTime() : number {
                    if (this.isEmpty()) {
                        return 0;
                    } else {
                        return new Date(this.$element.val()).getTime();
                    }
                }
                
                clearErrorStyle() : void {
                    this.$element.removeClass('error');
                }
            }
        }
    }
}
