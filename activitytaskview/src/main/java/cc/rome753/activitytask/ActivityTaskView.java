package cc.rome753.activitytask;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.Observable;
import java.util.TreeMap;

/**
 * Created by rome753@163.com on 2017/3/31.
 */

public class ActivityTaskView extends FrameLayout {

    public static final String TAG = ActivityTaskView.class.getSimpleName();
    private LinearLayout mLinearLayout;
    private View mTinyView;
    private View mTaskView;
    private FragmentListView mFragmentListView;
    private TreeMap<String, LinearLayout> mLayoutMap;
    private HashMap<String, ObserverTextView> mObserverTextViewMap;

    private ActivityLifecycleObservable mObservable;

    private int mStatusHeight;

    public ActivityTaskView(Context context) {
        super(context);
        inflate(context, R.layout.view_activity_task, this);
        mStatusHeight = AUtils.getStatusBarHeight(context);
        mLinearLayout = (LinearLayout) findViewById(R.id.ll);
        mFragmentListView = (FragmentListView) findViewById(R.id.flv);
        mTinyView = findViewById(R.id.tiny_view);
        mTaskView = findViewById(R.id.task_view);
        mObservable = new ActivityLifecycleObservable();
        mLayoutMap = new TreeMap<>();
        mObserverTextViewMap = new HashMap<>();
    }

    public FragmentListView getFragmentListView(){
        return mFragmentListView;
    }

    private void add(ATaskInfo taskInfo) {
        String name = taskInfo.getName();
        String parent = taskInfo.getParent();
        ObserverTextView textView = new ObserverTextView(getContext());
        textView.setText(name);
        mObserverTextViewMap.put(name, textView);
        LinearLayout layout = mLayoutMap.get(parent);
        if (layout == null) {
            layout = createLinearLayout();
            mLayoutMap.put(parent, layout);
            mLinearLayout.addView(layout);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layout.getLayoutParams();
            params.leftMargin = 2;
            layout.setLayoutParams(params);

            Log.i(TAG, "addLayout " + parent);
        }
        layout.addView(textView, 0);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) textView.getLayoutParams();
        textView.setLayoutParams(params);
        mObservable.addObserver(textView);
        Log.i(TAG, "addObserverTextView " + parent);
    }

    private void remove(ATaskInfo taskInfo) {
        String parent = taskInfo.getParent();
        LinearLayout layout = mLayoutMap.get(parent);
        if (layout == null) {
            Log.e(TAG, "LinearLayout not found");
            return;
        }
        ObserverTextView textView = mObserverTextViewMap.remove(taskInfo.getName());
        if (textView == null) {
            Log.e(TAG, "ObserverTextView not found");
            return;
        }
        layout.removeView(textView);
        Log.i(TAG, "removeObserverTextView " + parent);
        if (layout.getChildCount() == 0) {
            mLayoutMap.remove(parent);
            mLinearLayout.removeView(layout);
            Log.i(TAG, "removeLinearLayout " + parent);
        }
        showTinyOrNot();
        mObservable.deleteObserver(textView);
    }

    public void onActivityChange(ATaskInfo taskInfo){
        switch (taskInfo.getLife()){
            case 0:
                add(taskInfo);
                break;
            case 2:
                mFragmentListView.onActivityResume(taskInfo.getName());
                mObservable.lifecycleChange(taskInfo);
                break;
            case 5:
                mFragmentListView.onActivityDestroy(taskInfo.getName());
                remove(taskInfo);
                break;
            default:
                mObservable.lifecycleChange(taskInfo);
                break;
        }
    }


    public void onFragmentChange(ATaskInfo taskInfo){
        mFragmentListView.onFragmentChange(taskInfo);
    }

    private LinearLayout createLinearLayout() {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundResource(R.drawable.bg_rect_inner);
        layout.setPadding(10,0,10,0);
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

    static class ActivityLifecycleObservable extends Observable {

        void lifecycleChange(ATaskInfo info) {
            setChanged();
            notifyObservers(info);
        }
    }
}
