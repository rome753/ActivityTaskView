package cc.rome753.activitytask;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by rome753 on 2018/5/10.
 */

public class AUtils {

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

    public static void removeParent(View view) {
        if(view != null && view.getParent() instanceof ViewGroup) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }
}
