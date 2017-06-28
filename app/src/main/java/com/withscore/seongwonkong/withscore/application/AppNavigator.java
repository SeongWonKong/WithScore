package com.withscore.seongwonkong.withscore.application;

import android.app.Activity;
import android.content.Intent;

import com.withscore.seongwonkong.withscore.HomeActivity;

/**
 * Created by seongwonkong on 2017. 6. 28..
 */

public class AppNavigator {
    public static void goHomeActivity(Activity activity) {
        Intent intent = new Intent(activity, HomeActivity.class);
        activity.startActivity(intent);
        activity.finish();

    }
}
