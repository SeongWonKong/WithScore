package com.withscore.seongwonkong.withscore.application;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.toast.android.analytics.GameAnalytics;

/**
 * Created by seongwonkong on 2017. 6. 28..
 */

public class AppApplication extends Application {
    public static final String TAG = "AppApplication";
    public static Context sApplication;

    public AppApplication() {
        super();
    }

    public void onCreate() {
        super.onCreate();
        sApplication = this;
        init();
    }

    private void init() {
        initToastAnalytics();
    }

    private class GoogleAppIdTask extends AsyncTask<Void, Void, String> {
        protected String doInBackground(final Void... params) {
            String adId = null;
            try {
                adId = AdvertisingIdClient.getAdvertisingIdInfo(getApplicationContext()).getId();
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            return adId;
        }

        protected void onPostExecute(String adId) {
            // adId 필요한 부분에서 사용
        }
    }

    public void initToastAnalytics() {
        int result = GameAnalytics.initializeSdk(getApplicationContext(), "hC0VuKrrvcDYyzQt", "WHKv9Bi5lBQpPoSw", "1.0.0", false);

        if (result != GameAnalytics.S_SUCCESS) {
            Log.d(TAG, "initialize error " + GameAnalytics.getResultMessage(result));
        }
    }
}
