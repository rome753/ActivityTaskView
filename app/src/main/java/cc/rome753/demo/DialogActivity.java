package cc.rome753.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DialogActivity extends AppCompatActivity {

    public static void start(Activity activity){
        activity.startActivity(new Intent(activity, DialogActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,0);
    }
}
