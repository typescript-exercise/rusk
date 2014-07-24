angular
.module('rusk')
.service('removeTaskService', [
    'taskResource',
    function (taskResource : rusk.resource.task.TaskResource) {
        this.remove = (options) => {
            console.log('removeTaskService');
            var id = options.id;
            var title = options.title;
            var onRemove = options.onRemove;
            
            if (confirm('「' + title + '」を削除します。よろしいですか？')) {
                taskResource.remove(id,
                    function onSuccess() {
                        toastr.success('「' + title + '」を削除しました。');
                        
                        if (_.isFunction(onRemove)) {
                            onRemove();
                        }
                    },
                    function onNotFoundError() {
                        alert('指定したタスクは、既に削除されています。');
                    });
            }
        }
    }
]);