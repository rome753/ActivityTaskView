package cc.rome753.activitytask;

import android.content.Context;
import android.util.AttributeSet;

import java.util.Observable;
import java.util.Observer;

/**
 * change text color when update
 * Created by rome753@163.com on 2017/4/3.
 */

public class ObserverTextView extends FixedWidthTextView implements Observer{

    public ObserverTextView(Context context) {
        this(context, null);
    }

    public ObserverTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ObserverTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTextColor(AUtils.COLORS[0]);
    }

    @Override
    public void update(Observable o, Object arg){
        ATaskInfo info = (ATaskInfo) arg;
        if(info.getName().equals(getText().toString())) {
            setTextColor(AUtils.COLORS[info.getLife()]);
        }
    }

}
