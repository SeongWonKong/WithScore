package com.withscore.seongwonkong.withscore.application;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.withscore.seongwonkong.withscore.HomeActivity;
import com.withscore.seongwonkong.withscore.view.load_score.LoadScoreActivity;
import com.withscore.seongwonkong.withscore.view.load_score.SingleScoreActivity;

/**
 * Created by seongwonkong on 2017. 6. 28..
 */

public class AppNavigator {
    public static void goHomeActivity(Activity activity) {
        Intent intent = new Intent(activity, HomeActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void goLoadScoreActivity(Activity activity) {
        Intent intent = new Intent(activity, LoadScoreActivity.class);
        activity.startActivity(intent);
    }

    public static void goSingleScoreActivity(Activity activity, int drawableResId) {
        Intent intent = new Intent(activity, SingleScoreActivity.class);
        intent.putExtra("drawable", drawableResId);
        activity.startActivity(intent);
    }
}
