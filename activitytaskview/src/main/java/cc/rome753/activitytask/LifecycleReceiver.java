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
        if(TextUtils.equals(intent.getAction(), "action_update_lifecycle")) {
            String lifecycle = intent.getStringExtra("lifecycle");
            int task = intent.getIntExtra("task", 0);
            String activity = intent.getStringExtra("activity");
            ArrayList<String> fragments = intent.getStringArrayListExtra("fragments");
            String s = fragments == null ? "" : Arrays.toString(fragments.toArray());
            Log.d("chao", lifecycle + " " + task + " " + activity + " " + s);
        }
    }
}
