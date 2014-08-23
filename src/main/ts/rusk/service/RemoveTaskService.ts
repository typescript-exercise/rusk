/// <reference path="../resource/task/TaskResource.ts" />

module ruks {
    import TaskResource = rusk.resource.task.TaskResource;
    
    angular
    .module('rusk')
    .service('removeTaskService', [
        'taskResource',
        function (taskResource : TaskResource) {
            this.remove = (options) => {
                var id = options.id;
                var title = options.title;
                var onRemove = options.onRemove;
                
                if (confirm('「' + title + '」を削除します。よろしいですか？')) {
                    taskResource.remove({
                        id: id,
                        onSuccess: () => {
                            toastr.success('「' + title + '」を削除しました。');
                            
                            if (_.isFunction(onRemove)) {
                                onRemove();
                            }
                        },
                        onNotFoundError: () => {
                            alert('指定したタスクは、既に削除されています。');
                        }
                    });
                }
            }
        }
    ]);
}