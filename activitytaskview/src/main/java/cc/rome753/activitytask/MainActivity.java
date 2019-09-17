package cc.rome753.activitytask;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import cc.rome753.activitytask.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.btnPermission.setOnClickListener(v -> {
            requestWindowPermission();
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        checkWindowPermission();
    }

    private void requestWindowPermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        checkWindowPermission();
    }

    private void checkWindowPermission() {
        if (Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(this)) {
            binding.btnPermission.setVisibility(View.VISIBLE);
            binding.tvPermission.setVisibility(View.INVISIBLE);
        } else {
            binding.btnPermission.setVisibility(View.INVISIBLE);
            binding.tvPermission.setVisibility(View.VISIBLE);
            ActivityTask.start(this);
        }
    }
}
