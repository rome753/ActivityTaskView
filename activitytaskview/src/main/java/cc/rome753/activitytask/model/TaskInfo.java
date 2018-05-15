package cc.rome753.activitytask.model;

/**
 * Created by rome753 on 2017/3/31.
 */
public class TaskInfo {
    protected int life;
    protected String parent;
    protected String name;

    public TaskInfo(int life, String parent, String name) {
        this.life = life;
        this.parent = parent;
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }

    public int getLife() {
        return life;
    }

    public boolean isActivityLifecycle(){
        return !parent.contains("@");
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof TaskInfo && name.equals(((TaskInfo) obj).getName());
    }
}