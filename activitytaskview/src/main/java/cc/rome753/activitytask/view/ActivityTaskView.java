package cc.rome753.activitytask.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import cc.rome753.activitytask.AUtils;
import cc.rome753.activitytask.R;
import cc.rome753.activitytask.model.ATree;
import cc.rome753.activitytask.model.LifecycleInfo;

/**
 * Created by rome753 on 2017/3/31.
 */

public class ActivityTaskView extends FrameLayout {

    public static final String TAG = ActivityTaskView.class.getSimpleName();
    private LinearLayout mLinearLayout;
    private FrameLayout mContainer;
    private View mTinyView;
    private View mTaskView;

    private LifecycleObservable mObservable;

    private int mStatusHeight;

    public ActivityTaskView(Context context) {
        super(context);
        inflate(context, R.layout.view_activity_task, this);
        mStatusHeight = AUtils.getStatusBarHeight(context);
        mLinearLayout = findViewById(R.id.ll);
        mContainer = findViewById(R.id.fl);
        mTinyView = findViewById(R.id.tiny_view);
        mTaskView = findViewById(R.id.task_view);
        mObservable = new LifecycleObservable();
    }

    public FragmentTaskView findFragmentTaskView(String activity) {
        for(int i = 0; i < mContainer.getChildCount(); i++) {
            View view = mContainer.getChildAt(i);
            if (TextUtils.equals((CharSequence) view.getTag(), activity)) {
                return (FragmentTaskView) view;
            }
        }
        return null;
    }

    float mInnerX;
    float mInnerY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mInnerX = event.getX();
                mInnerY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float x = event.getRawX();
                float y = event.getRawY();
                WindowManager.LayoutParams params = (WindowManager.LayoutParams) getLayoutParams();
                params.x = (int) (x - mInnerX);
                params.y = (int) (y - mInnerY - mStatusHeight);
                updateLayout(params);
                break;
            case MotionEvent.ACTION_UP:
                showTinyOrNot();
                break;

        }
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    private void updateLayout(WindowManager.LayoutParams params){
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        if(windowManager != null) {
            windowManager.updateViewLayout(this, params);
        }
    }

    private void showTinyOrNot() {
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) getLayoutParams();
        if(p.x < 5 || mLinearLayout.getChildCount() == 0){
            mTinyView.setVisibility(VISIBLE);
            mTaskView.setVisibility(GONE);
            p.x = 0;
            updateLayout(p);
        }else{
            mTinyView.setVisibility(GONE);
            mTaskView.setVisibility(VISIBLE);
        }
    }

    private ATree aTree = new ATree();

    public void add(LifecycleInfo info) {
        FragmentTaskView view = new FragmentTaskView(getContext());
        view.setTag(info.activity);
        mContainer.addView(view);

        aTree.add(info.task, info.activity, info.lifecycle);
        notifyData();
    }

    public void remove(LifecycleInfo info) {
        mContainer.removeView(findFragmentTaskView(info.activity));

        aTree.remove(info.task, info.activity);
        notifyData();
    }

    public void update(LifecycleInfo info) {
        if(info.lifecycle.contains("Resume")) {
            FragmentTaskView view = findFragmentTaskView(info.activity);
            if(view != null) {
                view.setVisibility(VISIBLE);
            }
        } else if(info.lifecycle.contains("Pause")) {
            FragmentTaskView view = findFragmentTaskView(info.activity);
            if(view != null) {
                view.setVisibility(GONE);
            }
        }

        aTree.updateLifecycle(info.activity, info.lifecycle);
        notifyData();
    }

    private void notifyData() {
        mLinearLayout.removeAllViews();
        mObservable.deleteObservers();
        Set<Map.Entry<String, ArrayList<String>>> set = aTree.entrySet();
        for(Map.Entry<String, ArrayList<String>> entry : set) {
            TaskLayout layout = new TaskLayout(getContext());
            layout.setTitle(entry.getKey());
            for (String value : entry.getValue()) {
                ObserverTextView textView = new ObserverTextView(getContext());
                textView.setInfoText(value, aTree.getLifecycle(value));
                layout.add(textView);
                mObservable.addObserver(textView);
            }
            mLinearLayout.addView(layout, 0);
        }
    }

}
