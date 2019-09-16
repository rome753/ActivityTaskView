package cc.rome753.demo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class DemoFragmentActivity extends AppCompatActivity {

    List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_fragment);

//        replaceCleanFragment(R.id.content, DemoFragment.newInstance("", ""));


        fragmentList.add(PagerFragment.newInstance());
        for(int i = 0; i < 3; i++){
            fragmentList.add(DemoFragment.newInstance("", ""));
        }
        ViewPager vp = (ViewPager) findViewById(R.id.vp);
        vp.setOffscreenPageLimit(2);
        vp.setAdapter(new DemoAdapter(getSupportFragmentManager()));
    }

    class DemoAdapter extends FragmentStatePagerAdapter {

        public DemoAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

//    protected void replaceCleanFragment(int id, Fragment fragment) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(id, fragment, fragment.getClass().getName());
//        fragmentTransaction.commitAllowingStateLoss();
//        getSupportFragmentManager().executePendingTransactions();
//    }
}
