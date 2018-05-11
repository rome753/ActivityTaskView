package cc.rome753.activitytask;

public class ATaskInfo {
    private int life;
    private String parent;
    private String name;

    public ATaskInfo(int life, String parent, String name) {
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
        return obj instanceof ATaskInfo && name.equals(((ATaskInfo) obj).getName());
    }
}