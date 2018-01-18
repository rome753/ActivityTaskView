package cc.rome753.activitytaskview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

/**
 * change text color when update
 * Created by rome753 on 2017/4/3.
 */

public class ObserverTextView extends TextView implements Observer{

    public ObserverTextView(Context context) {
        super(context);
    }

    public ObserverTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObserverTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void update(Observable o, Object arg){
        ActivityTask.ColorInfo info = (ActivityTask.ColorInfo) arg;
        if(info.getActivityId() == (int)getTag()) {
            setTextColor(info.getColor());
        }
    }

}
