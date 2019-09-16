package cc.rome753.activitytask.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

import cc.rome753.activitytask.AUtils;
import cc.rome753.activitytask.model.TaskInfo;

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
        setMaxWidth(AUtils.getScreenWidth(context) / 2);
        setSingleLine();
        setEllipsize(TextUtils.TruncateAt.END);
        setTextColor(AUtils.COLORS[0]);
    }

    public void setShortText(String s){
        int index = s.indexOf("@");
        if(index > 0){
            s = s.substring(0, index);
        }
        setText(s);

    }

    @Override
    public void update(Observable o, Object arg){
        TaskInfo info = (TaskInfo) arg;
        if(TextUtils.equals(info.getName(), String.valueOf(getTag()))) {
            setTextColor(AUtils.COLORS[info.getLife()]);
        }
    }

}
