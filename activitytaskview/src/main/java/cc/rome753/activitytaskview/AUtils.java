package cc.rome753.activitytaskview;

import android.content.Context;

/**
 * Created by Administrator on 2018/5/10.
 */

public class AUtils {

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
