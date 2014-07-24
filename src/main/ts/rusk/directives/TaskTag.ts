angular
.module('rusk')
.directive('ruskTaskTag', ['taskResource', (taskResource) => {
    return {
        restrict: 'E',
        replace: true,
        scope: {
            task: '=',
            onRemove: '&'
        },
        templateUrl: 'directives/TaskTag.html',
        link: ($scope, $element, $attr) => {
            var task = $scope.task;
            var complete = task.completed;
            
            _.extend($scope, {
                panelClass: {
                    'panel-danger': !complete && task.rankS,
                    'panel-warning': !complete && task.rankA,
                    'panel-success': !complete && task.rankB,
                    'panel-info': !complete && task.rankC,
                    'panel-default': complete
                },
                
                remove: () => {
                    var id = task.id;
                    var title = task.title;
                    
                    if (confirm('「' + title + '」を削除します。よろしいですか？')) {
                    
                        taskResource.remove(id,
                            function onSuccess() {
                                toastr.success('「' + title + '」を削除しました。');
                                $scope.onRemove();
                            },
                            function onNotFoundError() {
                                alert('指定したタスクは、既に削除されています。');
                            });
                    }
                }
            });
        }
    };
}]);
