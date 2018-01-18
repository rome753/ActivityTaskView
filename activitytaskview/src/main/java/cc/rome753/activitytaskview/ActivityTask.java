package cc.rome753.activitytaskview;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.WindowManager;

import java.util.Observable;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by rome753@163.com on 2017/4/16.
 */

public class ActivityTask {

    private static final String TAG = ActivityTask.class.getSimpleName();

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

    private static Application.ActivityLifecycleCallbacks activityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            Log.w(TAG, activity.getClass().getName() + "@" + activity.hashCode() + " " + activity.getTaskId() + " " + " onActivityCreated");
            activityLifecycleObservable.lifecycleChange(new Pair<>(0, activity.hashCode()));
            activityTaskView.push(new ActivityTaskInfo(activity.getTaskId(), activity.hashCode(), activity.getClass().getSimpleName()));
        }

        @Override
        public void onActivityStarted(Activity activity) {
            Log.d(TAG, activity.getClass().getSimpleName() + " onActivityStarted");
            activityLifecycleObservable.lifecycleChange(new Pair<>(1, activity.hashCode()));

        }

        @Override
        public void onActivityResumed(Activity activity) {
            Log.d(TAG, activity.getClass().getSimpleName() + " onActivityResumed");
            activityLifecycleObservable.lifecycleChange(new Pair<>(2, activity.hashCode()));
            activityTaskView.setVisibility(VISIBLE);
            isFront = true;
        }

        @Override
        public void onActivityPaused(Activity activity) {
            Log.d(TAG, activity.getClass().getSimpleName() + " onActivityPaused");
            activityLifecycleObservable.lifecycleChange(new Pair<>(3, activity.hashCode()));
            isFront = false;
        }

        @Override
        public void onActivityStopped(Activity activity) {
            Log.d(TAG, activity.getClass().getSimpleName() + " onActivityStopped");
            activityLifecycleObservable.lifecycleChange(new Pair<>(4, activity.hashCode()));
            activityTaskView.setVisibility(isFront ? VISIBLE : GONE);

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            Log.d(TAG, activity.getClass().getSimpleName() + " onActivitySaveInstanceState");

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            Log.w(TAG, activity.getClass().getSimpleName() + " onActivityDestroyed");
            activityLifecycleObservable.lifecycleChange(new Pair<>(5, activity.hashCode()));
            activityTaskView.pop(new ActivityTaskInfo(activity.getTaskId(), activity.hashCode(), activity.getClass().getSimpleName()));
        }
    };

    static class ActivityLifecycleObservable extends Observable {

        /**
         * when activity lifecycle changed, notify the observer
         *
         * @param pair lifecycle:0-5/activityId:hashCode
         */
        void lifecycleChange(Pair<Integer, Integer> pair) {
            setChanged();
            notifyObservers(pair);
        }
    }

}
