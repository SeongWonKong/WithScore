package com.withscore.seongwonkong.withscore.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;

import com.toast.android.analytics.GameAnalytics;
import com.tsengvn.typekit.TypekitContextWrapper;
import com.withscore.seongwonkong.withscore.R;
import com.withscore.seongwonkong.withscore.application.AppApplication;
import com.withscore.seongwonkong.withscore.application.AppNavigator;
import com.withscore.seongwonkong.withscore.view.load_score.LoadScoreActivity;

import butterknife.ButterKnife;

/**
 * Created by seongwonkong on 2017. 6. 28..
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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

    @Override
    protected void attachBaseContext(Context newBase) {

        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));

    }
}
