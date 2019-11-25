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

        if(BuildConfig.DEBUG) {
            ActivityTaskHelper.init(this);
        }
    }

}
