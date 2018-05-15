package cc.rome753.activitytask.view;

import java.util.Observable;

import cc.rome753.activitytask.model.TaskInfo;

public class LifecycleObservable extends Observable {

    public void lifecycleChange(TaskInfo info) {
        setChanged();
        notifyObservers(info);
    }
}