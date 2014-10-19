/// <reference path="./NormalDate.ts" />

module rusk {
    export module model {
        export class FixdIntervalDate extends NormalDate {
            
            constructor(date : Date) {
                super(date);
                var minute = this.date.getMinutes();
                
                if (0 < minute && minute < 30) {
                    this.date.setMinutes(30);
                } else if (30 < minute && minute < 60) {
                    var hour = this.date.getHours();
                    this.date.setHours(hour + 1);
                    this.date.setMinutes(0);
                }
            }
        }
    }
}
