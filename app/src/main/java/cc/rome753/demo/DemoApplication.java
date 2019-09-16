package cc.rome753.demo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

/**
 * Created by rome753@163.com on 2017/3/23.
 */

public class DemoApplication extends Application{

    private static final String TAG = "ActivityTaskDemo";

    @Override
    public void onCreate() {
        super.onCreate();

        registerActivityLifecycleCallbacks(new ActivityLifecycleAdapter());
    }

    private void sendBroadcast(String lifecycle, Activity activity, Fragment fragment) {
        Intent intent = new Intent("action_update_lifecycle");
        intent.setPackage("cc.rome753.activitytask");
        intent.putExtra("lifecycle", lifecycle);
        intent.putExtra("task", activity.getTaskId());
        intent.putExtra("activity", activity.toString());
        if(fragment != null) {
            intent.putStringArrayListExtra("fragments", getAllFragments(fragment));
        }
        sendBroadcast(intent);
    }

    private ArrayList<String> getAllFragments(Fragment fragment){
        ArrayList<String> res = new ArrayList<>();
        while(fragment != null){
            res.add(AUtils.getSimpleName(fragment));
            fragment = fragment.getParentFragment();
        }
        return res;
    }

    private class ActivityLifecycleAdapter implements Application.ActivityLifecycleCallbacks{

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            if(activity instanceof FragmentActivity){
                ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(new FragmentLifecycleAdapter(), true);
            }
            String method = Thread.currentThread().getStackTrace()[2].getMethodName();
            sendBroadcast(method, activity, null);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            String method = Thread.currentThread().getStackTrace()[2].getMethodName();
            sendBroadcast(method, activity, null);
        }

        @Override
        public void onActivityResumed(Activity activity) {
            String method = Thread.currentThread().getStackTrace()[2].getMethodName();
            sendBroadcast(method, activity, null);
        }

        @Override
        public void onActivityPaused(Activity activity) {
            String method = Thread.currentThread().getStackTrace()[2].getMethodName();
            sendBroadcast(method, activity, null);
        }

        @Override
        public void onActivityStopped(Activity activity) {
            String method = Thread.currentThread().getStackTrace()[2].getMethodName();
            sendBroadcast(method, activity, null);
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            String method = Thread.currentThread().getStackTrace()[2].getMethodName();
            sendBroadcast(method, activity, null);
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            String method = Thread.currentThread().getStackTrace()[2].getMethodName();
            sendBroadcast(method, activity, null);
        }
    }


    private class FragmentLifecycleAdapter extends FragmentManager.FragmentLifecycleCallbacks{

        @Override
        public void onFragmentPreAttached(FragmentManager fm, Fragment f, Context context) {
            String method = Thread.currentThread().getStackTrace()[2].getMethodName();
            sendBroadcast(method, (Activity) context, f);
        }

        @Override
        public void onFragmentAttached(FragmentManager fm, Fragment f, Context context) {
            String method = Thread.currentThread().getStackTrace()[2].getMethodName();
            sendBroadcast(method, (Activity) context, f);
        }

        @Override
        public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
            String method = Thread.currentThread().getStackTrace()[2].getMethodName();
            sendBroadcast(method, f.getActivity(), f);
        }

        @Override
        public void onFragmentActivityCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
            String method = Thread.currentThread().getStackTrace()[2].getMethodName();
            sendBroadcast(method, f.getActivity(), f);
        }

        @Override
        public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
            String method = Thread.currentThread().getStackTrace()[2].getMethodName();
            sendBroadcast(method, f.getActivity(), f);
        }

        @Override
        public void onFragmentStarted(FragmentManager fm, Fragment f) {
            String method = Thread.currentThread().getStackTrace()[2].getMethodName();
            sendBroadcast(method, f.getActivity(), f);
        }

        @Override
        public void onFragmentResumed(FragmentManager fm, Fragment f) {
            String method = Thread.currentThread().getStackTrace()[2].getMethodName();
            sendBroadcast(method, f.getActivity(), f);
        }

        @Override
        public void onFragmentPaused(FragmentManager fm, Fragment f) {
            String method = Thread.currentThread().getStackTrace()[2].getMethodName();
            sendBroadcast(method, f.getActivity(), f);
        }

        @Override
        public void onFragmentStopped(FragmentManager fm, Fragment f) {
            String method = Thread.currentThread().getStackTrace()[2].getMethodName();
            sendBroadcast(method, f.getActivity(), f);
        }

        @Override
        public void onFragmentSaveInstanceState(FragmentManager fm, Fragment f, Bundle outState) {
            String method = Thread.currentThread().getStackTrace()[2].getMethodName();
            sendBroadcast(method, f.getActivity(), f);
        }

        @Override
        public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
            String method = Thread.currentThread().getStackTrace()[2].getMethodName();
            sendBroadcast(method, f.getActivity(), f);
        }

        @Override
        public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
            String method = Thread.currentThread().getStackTrace()[2].getMethodName();
            sendBroadcast(method, f.getActivity(), f);
        }

        @Override
        public void onFragmentDetached(FragmentManager fm, Fragment f) {
            String method = Thread.currentThread().getStackTrace()[2].getMethodName();
            sendBroadcast(method, f.getActivity(), f);
        }

    }

}
