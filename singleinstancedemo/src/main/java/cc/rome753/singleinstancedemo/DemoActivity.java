package cc.rome753.singleinstancedemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class DemoActivity extends AppCompatActivity {

    List<DemoFragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

//        replaceCleanFragment(R.id.content, DemoFragment.newInstance("", ""));


        for(int i = 0; i < 5; i++){
            fragmentList.add(DemoFragment.newInstance("", ""));
        }
        ViewPager vp = (ViewPager) findViewById(R.id.vp);
        vp.setOffscreenPageLimit(1);
        vp.setAdapter(new DemoAdapter(getSupportFragmentManager()));
    }

    class DemoAdapter extends FragmentStatePagerAdapter{

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
