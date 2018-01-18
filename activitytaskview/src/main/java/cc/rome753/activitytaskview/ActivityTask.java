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
import android.view.WindowManager;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by rome753@163.com on 2017/4/16.
 */

public class ActivityTask {

    private static final String TAG = ActivityTask.class.getSimpleName();

    private static final long DELAY = 300;

    private static final int[] COLORS = {
            0x00000000,//onCreate
            0x33ff0000,//onStart
            0xffff0000,//onResume

            0xff000000,//onPause
            0x33000000,//onStop
            0x00000000//onDestroy
    };

    private static ActivityTaskView activityTaskView;
    private static ActivityLifecycleObservable activityLifecycleObservable;

    /**
     * is current app front
     * <p>
     * front: Activity A onPause -> Activity B onResume -> Activity A onStop
     * <p>
     * not front: Activity A onPause -> Activity A onStop
     */
    private static boolean isFront;

    private static QueueHandler qhandler;

    /**
     * init in your application onCreate()
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
            addWindow(app);
        }
    }

    static void addWindow(Application app) {
        activityLifecycleObservable = new ActivityLifecycleObservable();
        activityTaskView = new ActivityTaskView(app);
        activityTaskView.setObservable(activityLifecycleObservable);

        qhandler = new QueueHandler();

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
        windowManager.addView(activityTaskView, params);

        app.registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }

    static class QueueHandler extends Handler{

        private Queue<Object> queue;
        private long lastTime;

        public QueueHandler(){
            super(Looper.getMainLooper());
            lastTime = 0;
            queue = new LinkedList<>();
        }

        public void add(Object o){
            queue.add(o);
            sendEmptyMessage(0);
        }

        @Override
        public void handleMessage(Message msg) {
            if(System.currentTimeMillis() - lastTime < DELAY){
                sendEmptyMessageDelayed(0, DELAY / 5);
            }else {
                Object obj = queue.poll();
                if(obj instanceof TaskInfo){
                    TaskInfo taskInfo = (TaskInfo) obj;
                    if(taskInfo.isOnCreate()){
                        activityTaskView.add(taskInfo);
                        activityLifecycleObservable.lifecycleChange(new ColorInfo(COLORS[0], taskInfo.activityId));
                    }else{
                        activityTaskView.remove(taskInfo);
                    }
                    lastTime = System.currentTimeMillis();
                }else if(obj instanceof ColorInfo){
                    activityLifecycleObservable.lifecycleChange((ColorInfo) obj);
                    lastTime = System.currentTimeMillis();
                }
            }
        }
    }

    private static Application.ActivityLifecycleCallbacks activityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            Log.w(TAG, activity.getClass().getName() + "@" + activity.hashCode() + " " + activity.getTaskId() + " " + " onActivityCreated");

            TaskInfo taskInfo = new TaskInfo(activity.getTaskId(), activity.hashCode(), activity.getClass().getSimpleName());
            taskInfo.setOnCreate(true);
            qhandler.add(taskInfo);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            Log.d(TAG, activity.getClass().getSimpleName() + " onActivityStarted");
            qhandler.add(new ColorInfo(COLORS[1], activity.hashCode()));
        }

        @Override
        public void onActivityResumed(Activity activity) {
            Log.d(TAG, activity.getClass().getSimpleName() + " onActivityResumed");
            qhandler.add(new ColorInfo(COLORS[2], activity.hashCode()));
            activityTaskView.setVisibility(VISIBLE);
            isFront = true;
        }

        @Override
        public void onActivityPaused(Activity activity) {
            Log.d(TAG, activity.getClass().getSimpleName() + " onActivityPaused");
            qhandler.add(new ColorInfo(COLORS[3], activity.hashCode()));
            isFront = false;
        }

        @Override
        public void onActivityStopped(Activity activity) {
            Log.d(TAG, activity.getClass().getSimpleName() + " onActivityStopped");
            qhandler.add(new ColorInfo(COLORS[4], activity.hashCode()));
            activityTaskView.setVisibility(isFront ? VISIBLE : GONE);

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            Log.d(TAG, activity.getClass().getSimpleName() + " onActivitySaveInstanceState");

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            Log.w(TAG, activity.getClass().getSimpleName() + " onActivityDestroyed");
            qhandler.add(new ColorInfo(COLORS[5], activity.hashCode()));
            qhandler.add(new TaskInfo(activity.getTaskId(), activity.hashCode(), activity.getClass().getSimpleName()));
        }
    };

    static class ActivityLifecycleObservable extends Observable {

        /**
         * when activity lifecycle changed, notify the observer
         *
         * @param info ColorInfo
         */
        void lifecycleChange(ColorInfo info) {
            setChanged();
            notifyObservers(info);
        }
    }

    static class TaskInfo {
        private int taskId;
        private int activityId;
        private String activityName;
        private boolean isOnCreate = false;

        public TaskInfo(int taskId, int activityId, String activityName) {
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

        public boolean isOnCreate() {
            return isOnCreate;
        }

        public void setOnCreate(boolean onCreate) {
            isOnCreate = onCreate;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof TaskInfo && taskId == ((TaskInfo) obj).getActivityId();
        }
    }

    static class ColorInfo{
        private int color;
        private int activityId;

        public ColorInfo(int color, int activityId) {
            this.color = color;
            this.activityId = activityId;
        }

        public int getColor() {
            return color;
        }

        public int getActivityId() {
            return activityId;
        }
    }

}
