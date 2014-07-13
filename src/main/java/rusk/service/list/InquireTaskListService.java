package rusk.service.list;

import javax.inject.Inject;

import rusk.domain.list.TaskList;
import rusk.domain.task.TaskRepository;

/**
 * タスク一覧取得サービス
 */
public class InquireTaskListService {
    
    private TaskRepository repository;
    
    @Inject
    public InquireTaskListService(TaskRepository repository) {
        this.repository = repository;
    }

    /**
     * タスク一覧を取得する。
     * 
     * @return タスク一覧
     */
    public TaskList inquire() {
        return TaskList.getTaskList(this.repository);
    }
}
