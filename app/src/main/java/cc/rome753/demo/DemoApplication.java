package cc.rome753.demo;

import android.app.Application;
import android.os.Build;

import cc.rome753.activitytaskview.ActivityTask;

/**
 * Created by petter on 2017/3/23.
 */

public class DemoApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        if(Build.VERSION.SDK_INT < 23) {
            ActivityTask.init(this, BuildConfig.DEBUG);
        }
    }

}
