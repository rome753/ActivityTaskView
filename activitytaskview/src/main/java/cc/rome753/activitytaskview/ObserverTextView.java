package cc.rome753.activitytaskview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

/**
 * change text color when update
 * Created by rome753@163.com on 2017/4/3.
 */

public class ObserverTextView extends TextView implements Observer{

    public ObserverTextView(Context context) {
        this(context, null);
    }

    public ObserverTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ObserverTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTextSize(12);
        setTextColor(Color.WHITE);
        setMaxLines(1);
        setBackgroundColor(Color.parseColor("#33AAAAAA"));
    }

    @Override
    public void update(Observable o, Object arg){
        ActivityTask.ColorInfo info = (ActivityTask.ColorInfo) arg;
        if(info.getActivityId() == (int)getTag()) {
            setTextColor(info.getColor());
        }
    }

}
