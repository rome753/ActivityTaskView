package cc.rome753.singleinstancedemo;

import android.app.Activity;
import android.app.Application;

import cc.rome753.activitytask.ActivityTask;


/**
 * Created by rome753@163.com on 2017/3/23.
 */

public class DemoApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        ActivityTask.init(this, BuildConfig.DEBUG);
        ActivityTask.setAutoHide(false);
    }

}
