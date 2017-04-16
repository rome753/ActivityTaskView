package cc.rome753.demo;

import android.app.Application;

import cc.rome753.activitytaskview.ActivityTask;

/**
 * Created by petter on 2017/3/23.
 */

public class DemoApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        ActivityTask.init(this, BuildConfig.DEBUG);

    }

}
