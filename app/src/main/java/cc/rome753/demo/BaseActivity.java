package cc.rome753.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by rome753@163.com on 2017/3/31.
 */

public class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getClass().getSimpleName());
        ActionBar actionBar = getSupportActionBar();
        if(actionBar == null){
            return;
        }
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void startDialogActivity(View v){
        DialogActivity.start(this);
    }

    public void startStandard(View v){
        StandardActivity.start(this);
    }

    public void startSingleTop(View v){
        SingleTopActivity.start(this);
    }

    public void startSingleTask(View v){
        SingleTaskActivity.start(this);
    }

    public void startSingleInstance(View v){
        SingleInstanceActivity.start(this);
    }
}
