package cc.rome753.singleinstancedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void openBaidu(View v){
        String url = "http://m.baidu.com/";
        Intent intent = new Intent(this, BrowserActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    public void openYouku(View v){
        String url = "http://m.youku.com/";
        Intent intent = new Intent(this, BrowserActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
