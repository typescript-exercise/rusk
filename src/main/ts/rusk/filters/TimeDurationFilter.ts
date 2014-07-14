angular
.module('rusk')
.filter('timeDuration', () => {
    return (millisecond : number) => {
        var hours = Math.floor(millisecond / 1000 / 60 / 60);
        var minute = Math.floor(millisecond / 1000 / 60) % 60;
        var second = Math.floor(millisecond / 1000) % 60;
        
        return pad(hours) + ':' + pad(minute) + ':' + pad(second);
    };
        
    function pad(num : number) {
        if (num < 10) {
            return '0' + num;
        } else {
            return num;
        }
    }
});
