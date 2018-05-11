package cc.rome753.activitytask;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * change text color when update
 * Created by rome753@163.com on 2017/4/3.
 */

public class FixedWidthTextView extends TextView{

    private static final int MAX_TEXT_SIZE = 16;
    private static final int MAX_WIDTH = AUtils.dp2px(150);

    public FixedWidthTextView(Context context) {
        super(context);
    }

    public FixedWidthTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FixedWidthTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setText(String text) {
        super.setText(text);
        int textSize = MAX_TEXT_SIZE;
        setTextSize(textSize);
        while (getPaint().measureText(text) > MAX_WIDTH){
            setTextSize(--textSize);
        }
    }
}
