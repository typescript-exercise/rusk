module rusk {
    export module model {
        export class NormalDate {
            date : Date;
            
            constructor(date : Date) {
                this.date = new Date(date.getTime());
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
