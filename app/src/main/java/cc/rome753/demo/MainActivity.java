package cc.rome753.demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cc.rome753.activitytaskview.ActivityTask;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;

    public void onClick(View v) {
        StandardActivity.start(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 23) {
            if (Settings.canDrawOverlays(this)) {
                ActivityTask.init(getApplication(), BuildConfig.DEBUG);
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, REQUEST_CODE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (requestCode == REQUEST_CODE && Settings.canDrawOverlays(this)) {
                ActivityTask.init(getApplication(), BuildConfig.DEBUG);
            }
        }
    }

}
