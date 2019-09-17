package cc.rome753.singleinstancedemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

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
