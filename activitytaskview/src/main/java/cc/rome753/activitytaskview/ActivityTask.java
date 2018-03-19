package cc.rome753.activitytaskview;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import java.util.LinkedList;
import java.util.Queue;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by rome753@163.com on 2017/4/16.
 */

public class ActivityTask {

    private static final String TAG = ActivityTask.class.getSimpleName();

    static int textSize = 12;

    static long interval = 300;

    private static boolean autoHide = true;

    private static ActivityTaskView activityTaskView;

    /**
     * Is current app front. If not, hide the activityTaskView.
     * <p>
     * front: Activity A onPause -> Activity B onResume -> Activity A onStop
     * <p>
     * not front: Activity A onPause -> Activity A onStop
     */
    private static boolean isFront;

    /**
     * Init in your application's onCreate()
     *
     * @param app   your application
     * @param debug your app module BuildConfig.DEBUG
     */
    public static void init(Application app, boolean debug) {
        if (!debug) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(app)) {
            Intent intent = new Intent(app, RequestOverlayActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            app.startActivity(intent);
        } else {
            start(app);
        }
    }

    /**
     * Set appearance
     * @param textSize textSize, default 12(sp)
     * @param interval interval between lifecycle, default 300(ms)
     * @param autoHide auto hide when app is not in foreground, default true
     */
    public static void setStyle(int textSize, long interval, boolean autoHide){
        ActivityTask.textSize = textSize;
        ActivityTask.interval = interval;
        ActivityTask.autoHide = autoHide;
    }

    static void start(Application app) {
        activityTaskView = new ActivityTaskView(app);
        addViewToWindow(app, activityTaskView);
        registerActivityLifecycleCallbacks(app);
    }

    private static void addViewToWindow(Application app, View view){
        WindowManager windowManager = (WindowManager) app.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.format = PixelFormat.RGBA_8888;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.START | Gravity.TOP;
        params.x = 0;
        params.y = app.getResources().getDisplayMetrics().heightPixels;
        if(windowManager != null) {
            windowManager.addView(view, params);
        }
    }

    private static void registerActivityLifecycleCallbacks(Application app){
        final QueueHandler queueHandler = new QueueHandler();
        app.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Log.w(TAG, activity.getClass().getName() + "@" + activity.hashCode() + " " + activity.getTaskId() + " " + " onActivityCreated");
                queueHandler.add(new TaskInfo(0, activity.getTaskId(), activity.hashCode(), activity.getClass().getSimpleName()));
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Log.d(TAG, activity.getClass().getSimpleName() + " onActivityStarted");
                queueHandler.add(new TaskInfo(1, activity.getTaskId(), activity.hashCode(), activity.getClass().getSimpleName()));
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Log.d(TAG, activity.getClass().getSimpleName() + " onActivityResumed");
                queueHandler.add(new TaskInfo(2, activity.getTaskId(), activity.hashCode(), activity.getClass().getSimpleName()));
                if(autoHide) {
                    activityTaskView.setVisibility(VISIBLE);
                    isFront = true;
                }
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Log.d(TAG, activity.getClass().getSimpleName() + " onActivityPaused");
                queueHandler.add(new TaskInfo(3, activity.getTaskId(), activity.hashCode(), activity.getClass().getSimpleName()));
                isFront = false;
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Log.d(TAG, activity.getClass().getSimpleName() + " onActivityStopped");
                queueHandler.add(new TaskInfo(4, activity.getTaskId(), activity.hashCode(), activity.getClass().getSimpleName()));
                if(autoHide){
                    activityTaskView.setVisibility(isFront ? VISIBLE : GONE);
                }

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                Log.d(TAG, activity.getClass().getSimpleName() + " onActivitySaveInstanceState");
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.w(TAG, activity.getClass().getSimpleName() + " onActivityDestroyed");
                queueHandler.add(new TaskInfo(5, activity.getTaskId(), activity.hashCode(), activity.getClass().getSimpleName()));
            }
        });
    }

    static class QueueHandler extends Handler{

        private Queue<TaskInfo> queue;
        private long lastTime;

        QueueHandler(){
            super(Looper.getMainLooper());
            lastTime = 0;
            queue = new LinkedList<>();
        }

        void add(TaskInfo taskInfo){
            queue.add(taskInfo);
            sendEmptyMessage(0);
        }

        @Override
        public void handleMessage(Message msg) {
            if(System.currentTimeMillis() - lastTime < interval){
                sendEmptyMessageDelayed(0, interval / 5);
            }else {
                lastTime = System.currentTimeMillis();
                TaskInfo taskInfo = queue.poll();
                if(taskInfo != null){
                    activityTaskView.lifecycleChange(taskInfo);
                }
            }
        }
    }

    static class TaskInfo {
        private int lifecycle;
        private int taskId;
        private int activityId;
        private String activityName;

        TaskInfo(int lifecycle, int taskId, int activityId, String activityName) {
            this.lifecycle = lifecycle;
            this.taskId = taskId;
            this.activityId = activityId;
            this.activityName = activityName;
        }

        public int getTaskId() {
            return taskId;
        }

        public int getActivityId() {
            return activityId;
        }

        public String getActivityName() {
            return activityName;
        }

        public int getLifecycle() {
            return lifecycle;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof TaskInfo && taskId == ((TaskInfo) obj).getActivityId();
        }
    }

}
