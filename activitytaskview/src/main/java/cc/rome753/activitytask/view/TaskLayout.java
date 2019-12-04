package cc.rome753.activitytask.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import cc.rome753.activitytask.AUtils;
import cc.rome753.activitytask.R;


/**
 * Created by rome753 on 2017/3/31.
 */

public class TaskLayout extends FrameLayout {

    LinearLayout ll;
    TextView tv;

    public TaskLayout(Context context) {
        this(context, null);
    }

    public TaskLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.layout_task, this);
        ll = findViewById(R.id.container);
        tv = findViewById(R.id.tv_title);
    }

    public void setTitle(String title) {
        tv.setText("[" + title + "]");
    }

    public void addFirst(View view) {
        ll.addView(view, 0);
    }

    public void addSecond(View view) {
        AUtils.removeParent(view);
        ll.addView(view, 1);
    }

}
