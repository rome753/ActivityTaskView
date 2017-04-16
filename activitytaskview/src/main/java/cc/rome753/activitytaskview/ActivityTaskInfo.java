package cc.rome753.activitytaskview;

/**
 * Created by rome753@163.com on 2017/4/4.
 */

public class ActivityTaskInfo {
    private int taskId;
    private int activityId;
    private String activityName;

    public ActivityTaskInfo(int taskId, int activityId, String activityName) {
        this.taskId = taskId;
        this.activityId = activityId;
        this.activityName = activityName;
    }

    public int getTaskId() {
        return taskId;
    }

    public int getActivityId() {
        return activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ActivityTaskInfo){
            return taskId == ((ActivityTaskInfo)obj).getActivityId();
        }
        return false;
    }
}
