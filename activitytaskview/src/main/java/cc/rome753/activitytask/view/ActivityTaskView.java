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

import cc.rome753.activitytask.AUtils;
import cc.rome753.activitytask.ActivityTask;
import cc.rome753.activitytask.R;
import cc.rome753.activitytask.model.FragmentInfo;
import cc.rome753.activitytask.model.TaskInfo;

/**
 * Created by rome753 on 2017/3/31.
 */

public class ActivityTaskView extends FrameLayout {

    public static final String TAG = ActivityTaskView.class.getSimpleName();
    private LinearLayout mLinearLayout;
    private View mTinyView;
    private View mTaskView;
    private FragmentTreeView mFragmentTreeView;

    private LifecycleObservable mObservable;

    private int mStatusHeight;

    public ActivityTaskView(Context context) {
        super(context);
        inflate(context, R.layout.view_activity_task, this);
        mStatusHeight = AUtils.getStatusBarHeight(context);
        mLinearLayout = (LinearLayout) findViewById(R.id.ll);
        mFragmentTreeView = (FragmentTreeView) findViewById(R.id.flv);
        mTinyView = findViewById(R.id.tiny_view);
        mTaskView = findViewById(R.id.task_view);
        mObservable = new LifecycleObservable();
    }

    private void add(TaskInfo taskInfo) {
        String name = taskInfo.getName();
        String parent = taskInfo.getParent();
        ObserverTextView textView = new ObserverTextView(getContext());
        textView.setTextSize(ActivityTask.getTextSize());
        textView.setShortText(name);
        textView.setTag(name);
        LinearLayout layout = (LinearLayout) findChildByTag(mLinearLayout, parent);
        if (layout == null) {
            layout = createLinearLayout(parent);
            mLinearLayout.addView(layout);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layout.getLayoutParams();
            params.topMargin = 2;
            layout.setLayoutParams(params);

            Log.i(TAG, "addLayout " + parent);
        }
        layout.addView(textView, 0);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) textView.getLayoutParams();
        textView.setLayoutParams(params);
        mObservable.addObserver(textView);
        Log.i(TAG, "addObserverTextView " + parent);
    }

    private View findChildByTag(ViewGroup viewGroup, String tag){
        View child = null;
        for(int i = 0; i < viewGroup.getChildCount(); i++){
            if(TextUtils.equals(tag, (CharSequence) viewGroup.getChildAt(i).getTag())){
                child = viewGroup.getChildAt(i);
            }
        }
        return child;
    }

    private void remove(TaskInfo taskInfo) {
        String parent = taskInfo.getParent();
        LinearLayout layout = (LinearLayout) findChildByTag(mLinearLayout, parent);
        if (layout == null) {
            Log.e(TAG, "LinearLayout not found");
            return;
        }

        ObserverTextView textView = (ObserverTextView) findChildByTag(layout, taskInfo.getName());
        if (textView == null) {
            Log.e(TAG, "ObserverTextView not found");
            return;
        }
        layout.removeView(textView);
        Log.i(TAG, "removeObserverTextView " + parent);
        if (layout.getChildCount() == 0) {
            mLinearLayout.removeView(layout);
            Log.i(TAG, "removeLinearLayout " + parent);
        }
        showTinyOrNot();
        mObservable.deleteObserver(textView);
    }

    public void onActivityChange(TaskInfo taskInfo){
        switch (taskInfo.getLife()){
            case 0:
                add(taskInfo);
                break;
            case 2:
                mFragmentTreeView.onActivityResume(taskInfo.getName());
                mObservable.lifecycleChange(taskInfo);
                break;
            case 5:
                mFragmentTreeView.onActivityDestroy(taskInfo.getName());
                remove(taskInfo);
                break;
            default:
                mObservable.lifecycleChange(taskInfo);
                break;
        }
    }


    public void onFragmentChange(FragmentInfo fragmentInfo){
        mFragmentTreeView.onFragmentChange(fragmentInfo);
    }

    private LinearLayout createLinearLayout(String tag) {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundResource(R.drawable.bg_rect_inner);
        layout.setPadding(10,0,10,0);
        layout.setTag(tag);
        return layout;
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

}
