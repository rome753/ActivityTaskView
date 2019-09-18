package cc.rome753.activitytask.view;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import java.util.Observable;
import java.util.Observer;

import cc.rome753.activitytask.model.LifecycleInfo;

/**
 * change text color when update
 * Created by rome753@163.com on 2017/4/3.
 */

public class ObserverTextView extends AppCompatTextView implements Observer{

    private static AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(8, true);

    public ObserverTextView(Context context) {
        this(context, null);
    }

    public ObserverTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ObserverTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setMaxLines(1);
        setTextSize(10);
    }

    public void setInfoText(String s, String lifecycle) {
        int i1 = s.indexOf("@");
        String tag = s.substring(i1);
        setTag(tag);

        s = s.replace("Activity", "A…");
        s = s.replace("Fragment", "F…");
        s = s.replace(tag, " ") + lifecycle;

        int i2 = s.indexOf(" ");

        setTextColor(s.contains("Resume") ? Color.RED : Color.BLACK);
        SpannableString span = new SpannableString(s);
        span.setSpan(absoluteSizeSpan, i2, s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(span);
    }


    @Override
    public void update(Observable o, Object arg){
        LifecycleInfo info = (LifecycleInfo) arg;
        String name;
        if(info.fragments != null) {
            name = info.fragments.get(0);
        } else {
            name = info.activity;
        }
        if(TextUtils.equals(name, String.valueOf(getTag()))) {
            String[] ss = getText().toString().split(" ");
            setText(ss[0] + " " + info.lifecycle);
        }
    }

}
