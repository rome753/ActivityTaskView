package cc.rome753.activitytask.model;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;

import cc.rome753.activitytask.view.ObserverTextView;

public class TextViewFractory {

    LinkedList<ObserverTextView> pool = new LinkedList<>();
    private static TextViewFractory factory = new TextViewFractory();
    public static TextViewFractory get() {
        return factory;
    }

    public void recycle(ViewGroup viewGroup) {
        if(viewGroup != null) {
            for(int i = 0; i < viewGroup.getChildCount(); i++) {
                View view = viewGroup.getChildAt(i);
                if(view instanceof ObserverTextView) {
                    pool.add((ObserverTextView) view);
                } else if(view instanceof ViewGroup) {
                    recycle((ViewGroup) view);
                }
            }
        }
    }

    public ObserverTextView getOne(Context context) {
        ObserverTextView view;
        if(pool.isEmpty()) {
            view = new ObserverTextView(context);
        } else {
            view = pool.remove();
            removeParent(view);
        }
        return view;
    }

    private void removeParent(View view) {
        if(view != null && view.getParent() instanceof ViewGroup) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }

}
