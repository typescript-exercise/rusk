module rusk {
    export module model {
        export class FixdIntervalDate {
            private date : Date;
            
            constructor(date : Date) {
                this.date = new Date(date.getTime());
                var minute = this.date.getMinutes();
                
                if (0 < minute && minute < 30) {
                    this.date.setMinutes(30);
                } else if (30 < minute && minute < 60) {
                    var hour = this.date.getHours();
                    this.date.setHours(hour + 1);
                    this.date.setMinutes(0);
                }
            }
            
            getDateAsString() : string {
                return rusk.formatDate(this.date, 'yyyy/MM/dd');
            }
            
            getTimeAsString() : string {
                return rusk.formatDate(this.date, 'HH:mm');
            }
            
            toString() : string {
                return rusk.formatDate(this.date, 'yyyy/MM/dd HH:mm');
            }
            
            getTime() : number {
                return this.date.getTime();
            }
            
            getDate() : Date {
                return new Date(this.getTime());
            }
        }
    }
}
