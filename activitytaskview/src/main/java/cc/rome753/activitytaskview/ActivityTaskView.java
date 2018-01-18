package cc.rome753.activitytaskview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.TreeMap;

/**
 * Created by rome753@163.com on 2017/3/31.
 */

public class ActivityTaskView extends LinearLayout {

    public static final String TAG = ActivityTaskView.class.getSimpleName();

    TreeMap<Integer, LinearLayout> mLayoutMap;

    HashMap<Integer, ObserverTextView> mObserverTextViewMap;

    private ActivityTask.ActivityLifecycleObservable mObservable;

    public ActivityTaskView(Context context) {
        this(context, null);
    }

    public ActivityTaskView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActivityTaskView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        setBackgroundColor(Color.parseColor("#33EEEEEE"));
        mLayoutMap = new TreeMap<>();
        mObserverTextViewMap = new HashMap<>();
    }

    public void setObservable(ActivityTask.ActivityLifecycleObservable observable) {
        mObservable = observable;
    }

    public void add(ActivityTask.TaskInfo taskInfo) {
        int activityId = taskInfo.getActivityId();
        int taskId = taskInfo.getTaskId();
        ObserverTextView textView = createObserverTextView(activityId, taskInfo.getActivityName());
        mObservable.addObserver(textView);
        mObserverTextViewMap.put(activityId, textView);
        LinearLayout layout = mLayoutMap.get(taskId);
        if (layout == null) {
            layout = createLinearLayout();
            mLayoutMap.put(taskId, layout);
            addView(layout);
            LinearLayout.LayoutParams params = (LayoutParams) layout.getLayoutParams();
            params.leftMargin = 2;
            layout.setLayoutParams(params);

            Log.i(TAG, "addLayout " + taskId);
        }
        layout.addView(textView, 0);
        LinearLayout.LayoutParams params = (LayoutParams) textView.getLayoutParams();
        params.bottomMargin = 1;
        textView.setLayoutParams(params);
        Log.i(TAG, "addObserverTextView " + taskId);
    }

    public void remove(ActivityTask.TaskInfo taskInfo) {
        int taskId = taskInfo.getTaskId();
        LinearLayout layout = mLayoutMap.get(taskId);
        if (layout == null) {
            Log.e(TAG, "LinearLayout not found");
            return;
        }
        ObserverTextView textView = mObserverTextViewMap.remove(taskInfo.getActivityId());
        if (textView == null) {
            Log.e(TAG, "ObserverTextView not found");
            return;
        }
        mObservable.deleteObserver(textView);
        layout.removeView(textView);
        Log.i(TAG, "removeObserverTextView " + taskId);
        if (layout.getChildCount() == 0) {
            mLayoutMap.remove(taskId);
            removeView(layout);
            Log.i(TAG, "removeLinearLayout " + taskId);
        }
    }

    private LinearLayout createLinearLayout() {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(VERTICAL);
        layout.setBackgroundColor(Color.parseColor("#33CCCCCC"));
        return layout;
    }

    private ObserverTextView createObserverTextView(int activityId, String text) {
        ObserverTextView textView = new ObserverTextView(getContext());
        textView.setText(text);
        textView.setTextSize(10);
        textView.setTextColor(Color.WHITE);
        textView.setBackgroundColor(Color.parseColor("#33AAAAAA"));
        textView.setTag(activityId);
        return textView;
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
                params.y = (int) (y - mInnerY);
                ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).updateViewLayout(this, params);
                break;
        }
        return true;
    }
}
