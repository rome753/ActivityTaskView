package cc.rome753.activitytaskview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

/**
 * change text color when update
 * Created by rome753@163.com on 2017/4/3.
 */

public class ObserverTextView extends TextView implements Observer{

    private static final int[] COLORS = {
            0x00000000,//onCreate
            0x33ff0000,//onStart
            0xffff0000,//onResume

            0xff000000,//onPause
            0x33000000,//onStop
            0x00000000//onDestroy
    };

    public ObserverTextView(Context context) {
        this(context, null);
    }

    public ObserverTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ObserverTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTextSize(ActivityTask.textSize);
        setTextColor(COLORS[0]);
        setMaxLines(1);
    }

    @Override
    public void update(Observable o, Object arg){
        ActivityTask.TaskInfo info = (ActivityTask.TaskInfo) arg;
        if(info.getName().equals(getText().toString())) {
            setTextColor(COLORS[info.getLifecycle()]);
        }
    }

}
