package cc.rome753.activitytask.view;

import android.content.Context;
import android.util.AttributeSet;

import java.util.List;

import cc.rome753.activitytask.model.FTree;
import cc.rome753.activitytask.model.LifecycleInfo;
import cc.rome753.activitytask.model.ViewPool;


/**
 * Created by rome753 on 2017/3/31.
 */

public class FragmentTaskView extends TaskLayout {

    FTree mTree = new FTree();

    public FragmentTaskView(Context context) {
        this(context, null);
    }

    public FragmentTaskView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setVisibility(GONE);
    }

    @Override
    public void setTitle(String title) {
        String[] ss = title.split("@");
        tv.setText(ss[0]);
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
        ViewPool.get().notifyLifecycleChange(info);
    }

    private void notifyData(){
        ViewPool.get().recycle(this);
        ll.removeAllViews();
        if(mTree != null){
            List<String> strings = mTree.convertToList();
            for(String s : strings){
                ATextView textView = ViewPool.get().getOne(getContext());
                String[] arr = s.split(String.valueOf('\u2500')); // -
                String name = arr[arr.length - 1];
                textView.setInfoText(s, mTree.getLifecycle(name));
                ll.addView(textView);
            }
        }
        setVisibility(ll.getChildCount() == 0 ? GONE : VISIBLE);
    }

}
