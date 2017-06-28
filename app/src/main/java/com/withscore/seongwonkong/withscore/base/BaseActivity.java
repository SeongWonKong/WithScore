package com.withscore.seongwonkong.withscore.base;

import android.support.v7.app.AppCompatActivity;

import com.toast.android.analytics.GameAnalytics;

/**
 * Created by seongwonkong on 2017. 6. 28..
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        if (GameAnalytics.isInitialized()) {
            GameAnalytics.traceActivation(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (GameAnalytics.isInitialized()) {
            GameAnalytics.traceDeactivation(this);
        }
    }
}
