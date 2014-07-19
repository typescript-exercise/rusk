module rusk {
    export function formatDate(date : Date, format : string) : string {
        return (<any>$).format.date(date, format);
    }
}
