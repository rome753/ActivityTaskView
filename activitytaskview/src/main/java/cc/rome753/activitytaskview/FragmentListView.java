package cc.rome753.activitytaskview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Administrator on 2018/5/10.
 */

public class FragmentListView extends ListView {

    List<String> mData;

    public FragmentListView(Context context) {
        super(context);
    }

    class FragmentAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if(view == null){

            }
            return null;
        }
    }
}
