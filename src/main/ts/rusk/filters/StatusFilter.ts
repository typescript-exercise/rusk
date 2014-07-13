angular
.module('rusk')
.filter('status', () => {
    return (status) => {
        switch (status) {
        case 'IN_WORKING':
            return '作業中';
        case 'UNSTARTED':
            return '未着手';
        case 'STOPPED':
            return '中断';
        case 'COMPLETE':
            return '完了';
        }
    };
});