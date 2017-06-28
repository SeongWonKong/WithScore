package com.withscore.seongwonkong.withscore;

import android.os.Bundle;
import android.util.Log;

import com.toast.android.analytics.GameAnalytics;
import com.withscore.seongwonkong.withscore.base.BaseActivity;

public class SplashActivity extends BaseActivity {
    public static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initToastAnalytics();

    }

    public void initToastAnalytics() {
        int result = GameAnalytics.initializeSdk(getApplicationContext(), "hC0VuKrrvcDYyzQt", "WHKv9Bi5lBQpPoSw", "1.0.0", false);

        if (result != GameAnalytics.S_SUCCESS) {
            Log.d(TAG, "initialize error " + GameAnalytics.getResultMessage(result));
        }
    }
}
