package rusk.domain.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Task {
    
    private Long id;
    private String title;
    private String detail;
    private Date registeredDate;
    private Date completedDate;
    private Priority priority;
    private Status status;
    private List<WorkTime> workTimes = new ArrayList<>();
    
    public Task(Date registeredDate) {
        this.setRegisteredDate(registeredDate);
    }
    
    public Task(long id, Date registeredDate) {
        this.setId(id);
        this.setRegisteredDate(registeredDate);
    }

    private void setId(long id) {
        Validate.isTrue(0 < id, "ID は 1 以上の値のみ受け付けます。");
        this.id = id;
    }

    private void setRegisteredDate(Date registeredDate) {
        Validate.notNull(registeredDate, "登録日は必須です。");
        this.registeredDate = new Date(registeredDate.getTime());
    }

    /**
     * このタスクを永続化させる。
     * 
     * @param repository 永続化のためのリポジトリ
     */
    public void register(TaskRepository repository) {
        long generatedId = repository.register(this);
        this.setId(generatedId);
    }
    
    /**
     * 指定した ID のタスクと、それに紐づく全ての作業時間を削除します。
     * 
     * @param repository タスクリポジトリ
     * @param deleteTargetTaskId 削除対象のタスク ID
     * @throws TaskNotFoundException 指定した ID に紐づくタスクが存在しない場合
     */
    public static void remove(TaskRepository repository, long deleteTargetTaskId) {
        repository.inquireWithLock(deleteTargetTaskId);
        repository.remove(deleteTargetTaskId);
    }
    
    /**
     * 作業時間を追加する。
     * @param time 作業時間
     * @throws DuplicateWorkTimeException 追加した作業時間が、既にこのタスクに登録されている作業時間と重複する場合
     */
    public void addWorkTime(WorkTime time) throws DuplicateWorkTimeException {
        Validate.notNull(time, "作業時間は必須です。");
        
        this.workTimes.forEach(workTime -> {
            if (workTime.isDuplicate(time)) {
                throw new DuplicateWorkTimeException(workTime, time);
            }
        });
        
        this.workTimes.add(time);
    }
    
    /**
     * このタスクの作業時間のリストのコピーを取得する。
     * @return 作業時間のリストのコピー。作業時間が存在しない場合、空のリストを返す。
     */
    public List<WorkTime> getWorkTimes() {
        return new ArrayList<>(this.workTimes);
    }

    /**
     * 作業時間を設定します。
     * <p>
     * null を渡した場合、空のリストで作業時間が上書きされます。
     * 
     * @param workTimes 作業時間
     * @throws DuplicateWorkTimeException リストの中に作業時間が重複する要素が存在する場合
     */
    public void setWorkTimes(List<WorkTime> workTimes) throws DuplicateWorkTimeException {
        if (workTimes == null) {
            this.workTimes = new ArrayList<>();
        } else {
            workTimes.forEach(one -> {
                workTimes
                    .stream()
                    .filter(other -> one != other)
                    .forEach(other -> {
                        if (one.isDuplicate(other)) {
                            throw new DuplicateWorkTimeException(one, other);
                        }
                    });
            });

            this.workTimes = new ArrayList<>(workTimes);
        }
    }

    /**
     * このタスクの作業時間の合計をミリ秒で取得する。
     * 
     * @return 作業時間の合計（ミリ秒）
     */
    public long getTotalWorkTime() {
        return this.workTimes.stream().collect(Collectors.summingLong(workTime -> workTime.getDuration()));
    }

    /**
     * 作業中の作業時間を取得します。
     * <p>
     * 作業中の作業時間とは、終了時間が設定されていない作業時間のことを表します。
     * <p>
     * 該当する作業時間が存在しない場合、 Null オブジェクトが返されます。<br>
     * Null オブジェクトは、全てのメソッドが何も処理を行わず、値を返すメソッドは null, 0, false のいずれかを返します。
     * 
     * @return 作業中の作業時間。
     */
    public WorkTime getWorkTimeInWorking() {
        Optional<WorkTime> result = this.workTimes.stream().filter(time -> !time.hasEndTime()).findAny();
        
        return result.isPresent() ? result.get() : new NullObjectWorkTime();
    }
    
    public void setTitle(String title) {
        Validate.notEmpty(title, "タイトルは必須です。");
        this.title = title;
    }
    
    public void setDetail(String detail) {
        this.detail = detail;
    }
    
    public void setCompletedDate(Date completedDate) {
        if (completedDate != null) {
            this.completedDate = new Date(completedDate.getTime());
        }
    }
    
    public void setPriority(Priority priority) {
        Validate.notNull(priority, "優先度は必須です。");
        this.priority = priority;
    }
    
    public void setStatus(Status status) {
        Validate.notNull(status, "状態は必須です。");
        this.status = status;
    }
    
    public Long getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getDetail() {
        return detail;
    }
    
    public Date getRegisteredDate() {
        return new Date(registeredDate.getTime());
    }
    
    public Date getCompletedDate() {
        return this.completedDate == null ? null : new Date(completedDate.getTime());
    }
    
    public Priority getPriority() {
        return priority;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public boolean isRankS() {
        return this.priority.is(Rank.S);
    }
    
    public boolean isRankA() {
        return this.priority.is(Rank.A);
    }
    
    public boolean isRankB() {
        return this.priority.is(Rank.B);
    }
    
    public boolean isRankC() {
        return this.priority.is(Rank.C);
    }
    
    public boolean isCompleted() {
        return this.status.isCompleted();
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
    
    /**
     * このコンストラクタはフレームワークのために存在します。
     */
    @Deprecated
    public Task() {
        this.registeredDate = null;
    }
}
