package cc.rome753.activitytask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class LifecycleReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String lifecycle = intent.getStringExtra("lifecycle");
        String task = intent.getStringExtra("task");
        String activity = intent.getStringExtra("activity");
        ArrayList<String> fragments = intent.getStringArrayListExtra("fragments");
        String s = fragments == null ? "" : Arrays.toString(fragments.toArray());
        Log.d("chao", lifecycle + " " + task + " " + activity + " " + s);

        ActivityTask.add(lifecycle, task, activity, fragments);
    }
}
