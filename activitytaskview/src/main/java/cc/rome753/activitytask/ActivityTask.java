package cc.rome753.activitytask;

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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import java.util.LinkedList;
import java.util.Queue;

import cc.rome753.activitytask.model.FragmentInfo;
import cc.rome753.activitytask.model.TaskInfo;
import cc.rome753.activitytask.view.ActivityTaskView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static cc.rome753.activitytask.AUtils.getSimpleName;

/**
 * Created by rome753@163.com on 2017/4/16.
 */

public class ActivityTask {

    private static final String TAG = ActivityTask.class.getSimpleName();

    private static ActivityTaskView activityTaskView;
    private static boolean autoHide = true;
    private static long interval = 100;
    private static int textSize = 12;

    /**
     * Is current app front. If not, hide the activityTaskView.
     * <p>
     * front: Activity A onPause -> Activity B onResume -> Activity A onStop
     * <p>
     * not front: Activity A onPause -> Activity A onStop
     */
    private static boolean isFront;

    /**
     * auto hide when app is not in foreground, default true
     */
    public static void setAutoHide(boolean autoHide) {
        ActivityTask.autoHide = autoHide;
    }

    /**
     * interval between lifecycle, default 100(ms)
     */
    public static void setInterval(long interval) {
        ActivityTask.interval = interval;
    }

    public static int getTextSize() {
        return textSize;
    }

    public static void setTextSize(int textSize) {
        ActivityTask.textSize = textSize;
    }

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

    static void start(Application app) {
        addViewToWindow(app, activityTaskView = new ActivityTaskView(app));
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
        params.y = app.getResources().getDisplayMetrics().heightPixels - 500;
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
                queueHandler.add(new TaskInfo(0, String.valueOf(activity.getTaskId()), getSimpleName(activity)));
                registerFragmentLifecycleCallbacks(activity, queueHandler);
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Log.d(TAG, activity.getClass().getSimpleName() + " onActivityStarted");
                queueHandler.add(new TaskInfo(1, String.valueOf(activity.getTaskId()), getSimpleName(activity)));
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Log.d(TAG, activity.getClass().getSimpleName() + " onActivityResumed");
                queueHandler.add(new TaskInfo(2, String.valueOf(activity.getTaskId()), getSimpleName(activity)));
                if(autoHide) {
                    activityTaskView.setVisibility(VISIBLE);
                    isFront = true;
                }
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Log.d(TAG, activity.getClass().getSimpleName() + " onActivityPaused");
                queueHandler.add(new TaskInfo(3, String.valueOf(activity.getTaskId()), getSimpleName(activity)));
                isFront = false;
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Log.d(TAG, activity.getClass().getSimpleName() + " onActivityStopped");
                queueHandler.add(new TaskInfo(4, String.valueOf(activity.getTaskId()), getSimpleName(activity)));
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
                queueHandler.add(new TaskInfo(5, String.valueOf(activity.getTaskId()), getSimpleName(activity)));
            }
        });
    }

    private static void registerFragmentLifecycleCallbacks(Activity activity, final QueueHandler queueHandler){
        if(activity instanceof FragmentActivity){
            ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
                @Override
                public void onFragmentPreAttached(FragmentManager fm, Fragment f, Context context) {
                    Log.d(TAG, getSimpleName(context) + ": " + Thread.currentThread().getStackTrace()[2].getMethodName());
                }

                @Override
                public void onFragmentAttached(FragmentManager fm, Fragment f, Context context) {
                    Log.d(TAG, getSimpleName(f) + ": " + Thread.currentThread().getStackTrace()[2].getMethodName());
                }

                @Override
                public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
                    Log.d(TAG, getSimpleName(f) + ": " + Thread.currentThread().getStackTrace()[2].getMethodName());
                    queueHandler.add(new FragmentInfo(0, f));

                }

                @Override
                public void onFragmentActivityCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
                    Log.d(TAG, getSimpleName(f) + ": " + Thread.currentThread().getStackTrace()[2].getMethodName());
                }

                @Override
                public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
                    Log.d(TAG, getSimpleName(f) + ": " + Thread.currentThread().getStackTrace()[2].getMethodName());
                }

                @Override
                public void onFragmentStarted(FragmentManager fm, Fragment f) {
                    Log.d(TAG, getSimpleName(f) + ": " + Thread.currentThread().getStackTrace()[2].getMethodName());
                    queueHandler.add(new FragmentInfo(1, f));
                }

                @Override
                public void onFragmentResumed(FragmentManager fm, Fragment f) {
                    Log.d(TAG, getSimpleName(f) + ": " + Thread.currentThread().getStackTrace()[2].getMethodName());
                    queueHandler.add(new FragmentInfo(2, f));
                }

                @Override
                public void onFragmentPaused(FragmentManager fm, Fragment f) {
                    Log.d(TAG, getSimpleName(f) + ": " + Thread.currentThread().getStackTrace()[2].getMethodName());
                    queueHandler.add(new FragmentInfo(3, f));
                }

                @Override
                public void onFragmentStopped(FragmentManager fm, Fragment f) {
                    Log.d(TAG, getSimpleName(f) + ": " + Thread.currentThread().getStackTrace()[2].getMethodName());
                    queueHandler.add(new FragmentInfo(4, f));
                }

                @Override
                public void onFragmentSaveInstanceState(FragmentManager fm, Fragment f, Bundle outState) {
                    Log.d(TAG, getSimpleName(f) + ": " + Thread.currentThread().getStackTrace()[2].getMethodName());
                }

                @Override
                public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
                    Log.d(TAG, getSimpleName(f) + ": " + Thread.currentThread().getStackTrace()[2].getMethodName());
                }

                @Override
                public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
                    Log.d(TAG, getSimpleName(f) + ": " + Thread.currentThread().getStackTrace()[2].getMethodName());
                    queueHandler.add(new FragmentInfo(5, f));
                }

                @Override
                public void onFragmentDetached(FragmentManager fm, Fragment f) {
                    Log.d(TAG, getSimpleName(f) + ": " + Thread.currentThread().getStackTrace()[2].getMethodName());
                }

            }, true);
        }
    }

    private static class QueueHandler extends Handler{

        private Queue<Object> queue;
        private long lastTime;

        QueueHandler(){
            super(Looper.getMainLooper());
            lastTime = 0;
            queue = new LinkedList<>();
        }

        void add(Object obj){
            queue.add(obj);
            sendEmptyMessage(0);
        }

        @Override
        public void handleMessage(Message msg) {
            if(System.currentTimeMillis() - lastTime < interval){
                sendEmptyMessageDelayed(0, interval / 5);
            }else {
                lastTime = System.currentTimeMillis();
                Object obj = queue.poll();
                if(obj instanceof FragmentInfo) {
                    activityTaskView.onFragmentChange((FragmentInfo) obj);
                } else if(obj instanceof TaskInfo) {
                    activityTaskView.onActivityChange((TaskInfo) obj);
                }
            }
        }
    }

}
