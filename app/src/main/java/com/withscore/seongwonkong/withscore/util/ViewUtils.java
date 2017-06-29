package com.withscore.seongwonkong.withscore.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.withscore.seongwonkong.withscore.application.AppApplication;

/**
 * Created by seongwonkong on 2017. 6. 30..
 */

public class ViewUtils {
    public static final int convertDpToPx(int dp) {
        return Math.round(dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static final float convertDpToPx(float dp) {
        return dp * Resources.getSystem().getDisplayMetrics().density;
    }

    public static int convertPxToDp(int px) {
        return Math.round(px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int getDisplayWidthPx(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public static int getDisplayHeightPx(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }
}
