package cc.rome753.activitytaskview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Pair;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

/**
 * change text color when update
 * Created by rome753 on 2017/4/3.
 */

public class ObserverTextView extends TextView implements Observer{

    private static final int[] COLORS = {
            Color.GREEN,//onCreate
            Color.YELLOW,//onStart
            Color.RED,//onResume

            Color.WHITE,//onPause
            Color.GRAY,//onStop
            Color.BLACK//onDestroy
    };

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
        Pair<Integer, Integer> pair = (Pair<Integer, Integer>) arg;
        if(pair.second == (int)getTag()) {
            setTextColor(COLORS[pair.first]);
        }
    }

}
