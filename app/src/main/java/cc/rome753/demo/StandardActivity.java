package cc.rome753.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class StandardActivity extends BaseActivity {

    public static void start(Activity activity){
        activity.startActivity(new Intent(activity, StandardActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard);
    }
}
