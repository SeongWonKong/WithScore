package com.withscore.seongwonkong.withscore.base;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.toast.android.analytics.GameAnalytics;

/**
 * Created by seongwonkong on 2017. 6. 28..
 */

public class BaseActivity extends AppCompatActivity {


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (getParentActivityIntent() == null) {
                    onBackPressed();
                } else {
                    NavUtils.navigateUpFromSameTask(this);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

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
