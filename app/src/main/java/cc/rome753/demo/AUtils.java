package cc.rome753.demo;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by rome753 on 2018/5/10.
 */

public class AUtils {

    public static final int[] COLORS = {
            0x00000000,//onCreate
            0x33ff0000,//onStart
            0xffff0000,//onResume

            0xff000000,//onPause
            0x33000000,//onStop
            0x00000000//onDestroy
    };

    public static int dp2px(float dpValue) {
        return (int) (0.5f + dpValue * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int getScreenWidth(Context context){
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static String getSimpleName(Object obj){
        return obj.getClass().getSimpleName() + "@" + Integer.toHexString(obj.hashCode());
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
