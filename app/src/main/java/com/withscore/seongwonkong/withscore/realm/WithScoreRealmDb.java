package com.withscore.seongwonkong.withscore.realm;

import android.content.Context;

import com.withscore.seongwonkong.withscore.WithScoreConstants;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class WithScoreRealmDb {
    public static void init(Context context) {
        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(WithScoreConstants.WITH_SCORE_DB)
                .build();
        Realm.setDefaultConfiguration(config);
}
}