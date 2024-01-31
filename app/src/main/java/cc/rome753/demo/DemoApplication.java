package cc.rome753.demo;

import android.app.Application;

/**
 * Created by rome753@163.com on 2017/3/23.
 */

public class DemoApplication extends Application{

    private static final String TAG = "ActivityTaskDemo";

    @Override
    public void onCreate() {
        super.onCreate();

//        if(BuildConfig.DEBUG) {
//            ActivityTaskHelper.init(this);
//        }
    }

}
