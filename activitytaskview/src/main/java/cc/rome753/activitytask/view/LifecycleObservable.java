package cc.rome753.activitytask.view;

import java.util.Observable;

import cc.rome753.activitytask.model.LifecycleInfo;

public class LifecycleObservable extends Observable {

    public void lifecycleChange(LifecycleInfo info) {
        setChanged();
        notifyObservers(info);
    }
}