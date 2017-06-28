package com.withscore.seongwonkong.withscore;

import android.os.Bundle;
import android.os.Handler;

import com.withscore.seongwonkong.withscore.application.AppNavigator;
import com.withscore.seongwonkong.withscore.base.BaseActivity;

public class SplashActivity extends BaseActivity {
    public static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        goHomeActivity();
    }

    private void goHomeActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AppNavigator.goHomeActivity(SplashActivity.this);
            }
        }, 1000);
    }

}
