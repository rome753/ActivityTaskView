package cc.rome753.activitytask.view;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * change text color when update
 * Created by rome753@163.com on 2017/4/3.
 */

public class ATextView extends AppCompatTextView{

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

        lifecycle = lifecycle.replace("onFragment", "");
        lifecycle = lifecycle.replace("onActivity", "");
        lifecycle = lifecycle.replace("SaveInstanceState", "SIS");
        s = s.replace("Activity", "A…");
        s = s.replace("Fragment", "F…");
        s = s.replace(tag, " ") + lifecycle;

        int i2 = s.indexOf(" ");

        setTextColor(s.contains("Resume") ? Color.YELLOW : Color.WHITE);
        SpannableString span = new SpannableString(s);
        span.setSpan(absoluteSizeSpan, i2, s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(span);
    }

}
