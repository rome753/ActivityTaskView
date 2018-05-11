package cc.rome753.activitytask;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/5/10.
 */

public class FragmentListView extends ListView {

    HashMap<String, List<String>> mAFMap;//activity-fragmentList
    HashMap<String, Integer> mFLMap;//fragment-life
    List<String> mData;
    BaseAdapter mAdapter;

    public FragmentListView(Context context) {
        this(context, null);
    }

    public FragmentListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mAFMap = new HashMap<>();
        mFLMap = new HashMap<>();
        setAdapter(mAdapter = new FragmentAdapter());
    }

    class FragmentAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mData == null ? 0 : mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData == null ? null : mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if(view == null){
                view = new FixedWidthTextView(getContext());
            }
            String name = (String) getItem(position);
            int life = mFLMap.get(name);
            ((FixedWidthTextView)view).setText(name);
            ((FixedWidthTextView)view).setTextColor(AUtils.COLORS[life]);
            return view;
        }
    }

    public void onActivityResume(String activityName){
        mData = mAFMap.get(activityName);
        if(mData == null){
            mData = new ArrayList<>();
            mAFMap.put(activityName, mData);
        }
        mAdapter.notifyDataSetChanged();
    }

    public void onActivityDestroy(String activityName){
        mAFMap.remove(activityName);
    }

    public void onFragmentChange(ATaskInfo taskInfo){
        String parent = taskInfo.getParent();
        String name = taskInfo.getName();
        int life = taskInfo.getLife();
        List<String> fragments = mAFMap.get(parent);
        if(fragments == null){
            fragments = new ArrayList<>();
            mAFMap.put(parent, fragments);
        }

        switch (life){
            case 0:
                fragments.add(name);
                mFLMap.put(name, life);
                break;
            case 5:
                fragments.remove(name);
                mFLMap.remove(name);
                break;
            default:
                mFLMap.put(name, life);
                break;
        }

        if(mData == fragments){
            mAdapter.notifyDataSetChanged();
        }
    }

}
