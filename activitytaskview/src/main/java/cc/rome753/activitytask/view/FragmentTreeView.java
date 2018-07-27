package cc.rome753.activitytask.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import cc.rome753.activitytask.AUtils;
import cc.rome753.activitytask.ActivityTask;
import cc.rome753.activitytask.R;
import cc.rome753.activitytask.model.FragmentInfo;
import cc.rome753.activitytask.model.STree;


/**
 * Created by rome753 on 2017/3/31.
 */

public class FragmentTreeView extends LinearLayout {

    HashMap<String, STree> mAFMap;      //activity-fragmentList
    HashMap<String, Integer> mFLMap;    //fragment-life
    STree mTree;

    LifecycleObservable mLifecycleObservable;

    public FragmentTreeView(Context context) {
        this(context, null);
    }

    public FragmentTreeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        mLifecycleObservable = new LifecycleObservable();
        mAFMap = new HashMap<>();
        mFLMap = new HashMap<>();
    }

    private void notifyData(){
        removeAllViews();
        mLifecycleObservable.deleteObservers();
        if(mTree != null){
            List<String> strings = mTree.convertToList();
            for(String s : strings){
                addTextView(s);
            }
        }
    }

    private void addTextView(String text){
        ObserverTextView textView = new ObserverTextView(getContext());
        textView.setTextSize(ActivityTask.getTextSize());
        String[] arr = text.split(String.valueOf('\u2500')); // -
        String name = arr[arr.length - 1];
        int life = mFLMap.containsKey(name) ? mFLMap.get(name) : 0;
        textView.setTag(name);
        textView.setShortText(text);
        textView.setTextColor(AUtils.COLORS[life]);
        textView.setMaxLines(1);
        addView(textView);
        mLifecycleObservable.addObserver(textView);
    }

    public void onActivityResume(String activityName){
        mTree = mAFMap.get(activityName);
        if(mTree == null){
            mTree = new STree();
            mAFMap.put(activityName, mTree);
        }
        notifyData();
    }

    public void onActivityDestroy(String activityName){
        mAFMap.remove(activityName);
    }

    public void onFragmentChange(FragmentInfo fragmentInfo){
        int life = fragmentInfo.getLife();
        String parent = fragmentInfo.getParent();
        String name = fragmentInfo.getName();
        LinkedList<String> list = fragmentInfo.getFragments();

        STree stree = mAFMap.get(parent);
        if(stree == null){
            stree = new STree();
            mAFMap.put(parent, stree);
        }

        switch (life){
            case 0:
                stree.add(list);
                mFLMap.put(name, life);
                if(stree == mTree){
                    notifyData();
                }
                break;
            case 5:
                stree.remove(list);
                mFLMap.remove(name);
                if(stree == mTree){
                    notifyData();
                }
                break;
            default:
                mFLMap.put(name, life);
                mLifecycleObservable.lifecycleChange(fragmentInfo);
                break;
        }
    }

}
