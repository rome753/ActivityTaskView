package cc.rome753.activitytask.view;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import cc.rome753.activitytask.AUtils;
import cc.rome753.activitytask.R;
import cc.rome753.activitytask.model.ATree;
import cc.rome753.activitytask.model.LifecycleInfo;
import cc.rome753.activitytask.model.ViewPool;

/**
 * Created by rome753 on 2017/3/31.
 */

public class ActivityTaskView extends LinearLayout {

    public static final String TAG = ActivityTaskView.class.getSimpleName();
    private ViewGroup mLinearLayout;
    private View mTinyView;
    private View mTaskView;
    private View mEmptyView;

    private HashSet<String> mPendingRemove; // Wait for fragments destroy

    private int mStatusHeight;
    private int mScreenWidth;

    public ActivityTaskView(Context context) {
        super(context);
        inflate(context, R.layout.view_activity_task, this);
        mStatusHeight = AUtils.getStatusBarHeight(context);
        mScreenWidth = AUtils.getScreenWidth(context);
        mLinearLayout = findViewById(R.id.container);
        mTinyView = findViewById(R.id.tiny_view);
        mTaskView = findViewById(R.id.main_view);
        mEmptyView = findViewById(R.id.view_empty);
        mPendingRemove = new HashSet<>();
    }

    float mInnerX;
    float mInnerY;
    long downTime;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downTime = System.currentTimeMillis();
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
                if(System.currentTimeMillis() - downTime < 100
                        && Math.abs(event.getX() - mInnerX) < 10
                        && Math.abs(event.getY() - mInnerY) < 10) {
                    doClick();
                }
                moveToBorder();
                break;

        }
        return true;
    }

    private void doClick() {
        boolean visible = mTaskView.getVisibility() == VISIBLE;
        mTaskView.setVisibility(visible ? GONE : VISIBLE);
        mTinyView.setVisibility(!visible ? GONE : VISIBLE);
    }

    private void updateLayout(WindowManager.LayoutParams params){
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        if(windowManager != null) {
            windowManager.updateViewLayout(this, params);
        }
    }

    private void moveToBorder() {
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) getLayoutParams();
        Log.d("chao", "x " + p.x + " " + ((mScreenWidth - getWidth()) / 2));

        if(p.x <= (mScreenWidth - getWidth()) / 2) { // move left
            p.x = 0;
        } else { // move right
            p.x = mScreenWidth;
        }
        updateLayout(p);
    }

    private ATree aTree = new ATree();

    public void add(LifecycleInfo info) {
        aTree.add(info.task, info.activity, info.lifecycle);
        notifyData();
    }

    public void remove(LifecycleInfo info) {
        if(ViewPool.get().getF(info.activity) != null) {
            mPendingRemove.add(info.activity);
            update(info);
        } else {
            aTree.remove(info.task, info.activity);
            notifyData();
        }
    }

    public void update(LifecycleInfo info) {
        aTree.updateLifecycle(info.activity, info.lifecycle);
        ViewPool.get().notifyLifecycleChange(info);
    }

    public void addF(LifecycleInfo info) {
        FragmentTaskView view = ViewPool.get().getF(info.activity);
        if(view == null) {
            view = ViewPool.get().addF(getContext(), info.activity);
            notifyData();
        }
        view.add(info);
    }

    public void removeF(LifecycleInfo info) {
        FragmentTaskView view = ViewPool.get().getF(info.activity);
        if(view != null) {
            view.remove(info);
            if(view.getChildCount() == 0 && mPendingRemove.contains(info.activity)) {
                mPendingRemove.remove(info.activity);
                remove(info);
            }
        }
    }

    public void updateF(LifecycleInfo info) {
        FragmentTaskView view = ViewPool.get().getF(info.activity);
        if(view != null) {
            view.update(info);
        }
    }

    private void notifyData() {
        ViewPool.get().recycle(mLinearLayout);
        mLinearLayout.removeAllViews();
        for(Map.Entry<String, ArrayList<String>> entry : aTree.entrySet()) {
            TaskLayout taskLayout = new TaskLayout(getContext());
            taskLayout.setTitle(entry.getKey());
            for (String activity : entry.getValue()) {
                ATextView textView = ViewPool.get().getOne(getContext());
                textView.setInfoText(activity, aTree.getLifecycle(activity));
                taskLayout.addFirst(textView);

                FragmentTaskView view = ViewPool.get().getF(activity);
                if(view != null) {
                    taskLayout.addSecond(view);
                }
            }
            mLinearLayout.addView(taskLayout, 0);
        }
        mEmptyView.setVisibility(mLinearLayout.getChildCount() == 0 ? VISIBLE : GONE);

    }

    public void clear() {
        ViewPool.get().clearF();
        aTree = new ATree();
        notifyData();
    }

}
