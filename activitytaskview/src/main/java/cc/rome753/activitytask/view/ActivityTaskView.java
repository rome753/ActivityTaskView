package cc.rome753.activitytask.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
import cc.rome753.activitytask.model.TextViewFractory;

/**
 * Created by rome753 on 2017/3/31.
 */

public class ActivityTaskView extends LinearLayout {

    public static final String TAG = ActivityTaskView.class.getSimpleName();
    private ViewGroup mLinearLayout;
    private ViewGroup mContainer;
    private View mTinyView;
    private View mTaskView;
    private View mEmptyView;


    private int mStatusHeight;
    private int mScreenWidth;

    public ActivityTaskView(Context context) {
        super(context);
        inflate(context, R.layout.view_activity_task, this);
        mStatusHeight = AUtils.getStatusBarHeight(context);
        mScreenWidth = AUtils.getScreenWidth(context);
        mLinearLayout = findViewById(R.id.ll);
        mContainer = findViewById(R.id.fl);
        mTinyView = findViewById(R.id.tiny_view);
        mTaskView = findViewById(R.id.task_view);
        mEmptyView = findViewById(R.id.view_empty);
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
            removeView(mTinyView);
            addView(mTinyView, 0);

        } else { // move right
            p.x = mScreenWidth;
            removeView(mTinyView);
            addView(mTinyView);
        }
        updateLayout(p);
    }

    private ATree aTree = new ATree();

    public void add(LifecycleInfo info) {
        FragmentTaskView view = new FragmentTaskView(getContext());
        view.setTag(info.activity);
        mContainer.addView(view, 0);

        aTree.add(info.task, info.activity, info.lifecycle);
        notifyData();
    }

    public void remove(LifecycleInfo info) {
        FragmentTaskView view = findFragmentTaskView(info.activity);
        TextViewFractory.get().recycle(view);
        mContainer.removeView(view);

        aTree.remove(info.task, info.activity);
        notifyData();
    }

    public void update(LifecycleInfo info) {
        aTree.updateLifecycle(info.activity, info.lifecycle);
        notifyData();
    }

    private void notifyData() {
        TextViewFractory.get().recycle(mLinearLayout);
        mLinearLayout.removeAllViews();
        Set<Map.Entry<String, ArrayList<String>>> set = aTree.entrySet();
        for(Map.Entry<String, ArrayList<String>> entry : set) {
            TaskLayout layout = new TaskLayout(getContext());
            layout.setTitle(entry.getKey());
            for (String value : entry.getValue()) {
                ObserverTextView textView = TextViewFractory.get().getOne(getContext());
                textView.setInfoText(value, aTree.getLifecycle(value));
                layout.addFirst(textView);
            }
            mLinearLayout.addView(layout, 0);
        }
        mEmptyView.setVisibility(mLinearLayout.getChildCount() == 0 ? VISIBLE : GONE);

    }

    public void clear() {
        aTree = new ATree();
        mContainer.removeAllViews();
        notifyData();
    }

}
