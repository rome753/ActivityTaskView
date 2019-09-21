package cc.rome753.activitytask.model;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observable;

import cc.rome753.activitytask.view.ATextView;
import cc.rome753.activitytask.view.FragmentTaskView;

public class ViewPool extends Observable {

    LinkedList<ATextView> pool = new LinkedList<>();
    HashMap<String,FragmentTaskView> map = new HashMap<>();

    private static ViewPool factory = new ViewPool();
    public static ViewPool get() {
        return factory;
    }

    public void recycle(ViewGroup viewGroup) {
        if(viewGroup != null) {
            for(int i = 0; i < viewGroup.getChildCount(); i++) {
                View view = viewGroup.getChildAt(i);
                if(view instanceof ATextView) {
                    removeParent(view);
                    view.setTag(null);
                    pool.add((ATextView) view);
                } else if(view instanceof FragmentTaskView) {
                    // don't recycle
                } else if(view instanceof ViewGroup) {
                    recycle((ViewGroup) view);
                }
            }
        }
    }

    public ATextView getOne(Context context) {
        ATextView view;notifyObservers();
        if(pool.isEmpty()) {
            view = new ATextView(context);
            addObserver(view);
        } else {
            view = pool.remove();
        }
        return view;
    }

    private void removeParent(View view) {
        if(view != null && view.getParent() instanceof ViewGroup) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }

    public void notifyLifecycleChange(LifecycleInfo info) {
        setChanged();
        notifyObservers(info);
    }


    public FragmentTaskView getF(String activity) {
        return map.get(activity);
    }

    public FragmentTaskView addF(Context context, String activity) {
        FragmentTaskView view = new FragmentTaskView(context);
        map.put(activity, view);
        return view;
    }

    public void removeF(String activity) {
        map.remove(activity);
    }

    public void clearF() {
        map.clear();
    }
}
