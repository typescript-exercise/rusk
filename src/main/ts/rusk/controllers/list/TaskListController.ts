angular
.module('rusk')
.controller('TaskListController', [
    '$scope', 'taskListResource', 'taskResource',
    ($scope, taskListResource : rusk.resource.list.TaskListResource, taskResource : rusk.resource.task.TaskResource) => {
        
        _.extend($scope, {
            inquireTaskList: inquireTaskList,
            remove: (id : number, title : string) => {
                if (confirm('「' + title + '」を削除します。よろしいですか？')) {
                    taskResource.remove(id,
                        function onSuccess() {
                            toastr.success('「' + title + '」を削除しました。');
                            inquireTaskList();
                        },
                        function onNotFoundError() {
                            alert('指定したタスクは、既に削除されています。');
                        });
                }
            }
        });
        
        inquireTaskList();
        
        function inquireTaskList() {
            taskListResource.inquire( taskList => {
                $scope.taskList = taskList;
                $scope.inWorking = taskList.taskInWorking;
            });
        }
    }
]);