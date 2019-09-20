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

public class ATextView extends AppCompatTextView implements Observer {

    private static AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(8, true);

    public ATextView(Context context) {
        this(context, null);
    }

    public ATextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ATextView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        s = s.replace(tag, " ");

        addLifecycle(s, lifecycle);
    }

    private void addLifecycle(String s, String lifecycle) {
        lifecycle = lifecycle.replace("onFragment", "");
        lifecycle = lifecycle.replace("onActivity", "");
        lifecycle = lifecycle.replace("SaveInstanceState", "SIS");

        setTextColor(lifecycle.contains("Resume") ? Color.YELLOW : Color.WHITE);
        SpannableString span = new SpannableString(s + lifecycle);
        span.setSpan(absoluteSizeSpan, s.length(), span.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(span);
    }

    @Override
    public void update(Observable o, Object arg) {
        if(getTag() == null) {
            return;
        }
        if(arg instanceof LifecycleInfo) {
            LifecycleInfo info = (LifecycleInfo) arg;
            String s = info.fragments != null ? info.fragments.get(0) : info.activity;
            int i1 = s.indexOf("@");
            String tag = s.substring(i1);
            if(TextUtils.equals((CharSequence) getTag(), tag)) {
                s = getText().toString();
                s = s.substring(0, s.lastIndexOf(" ") + 1);
                addLifecycle(s, info.lifecycle);
            }
        }
    }
}
