package cc.rome753.activitytask.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.List;

import cc.rome753.activitytask.model.FTree;
import cc.rome753.activitytask.model.LifecycleInfo;
import cc.rome753.activitytask.model.ViewPool;


/**
 * Created by rome753 on 2017/3/31.
 */

public class FragmentTaskView extends LinearLayout {

    FTree mTree = new FTree();

    public FragmentTaskView(Context context) {
        this(context, null);
    }

    public FragmentTaskView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        setBackgroundColor(Color.parseColor("#333333"));
    }

    public void add(LifecycleInfo info) {
        mTree.add(info.fragments, info.lifecycle);
        notifyData();
    }

    public void remove(List<String> fragments) {
        mTree.remove(fragments);
        notifyData();
    }

    public void update(LifecycleInfo info) {
        mTree.updateLifecycle(info.fragments.get(0), info.lifecycle);
        notifyData();
    }

    private void notifyData(){
        ViewPool.get().recycle(this);
        removeAllViews();
        if(mTree != null){
            List<String> strings = mTree.convertToList();
            for(String s : strings){
                ObserverTextView textView = ViewPool.get().getOne(getContext());
                String[] arr = s.split(String.valueOf('\u2500')); // -
                String name = arr[arr.length - 1];
                textView.setInfoText(s, mTree.getLifecycle(name));
                addView(textView);
            }
        }
    }

}
