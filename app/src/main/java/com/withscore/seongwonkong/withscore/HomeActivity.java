package com.withscore.seongwonkong.withscore;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.withscore.seongwonkong.withscore.application.AppNavigator;
import com.withscore.seongwonkong.withscore.base.BaseActivity;
import com.withscore.seongwonkong.withscore.realm.WithScoreRealmDbHelper;
import com.withscore.seongwonkong.withscore.realm.model.Score;
import com.withscore.seongwonkong.withscore.util.ViewUtils;
import com.withscore.seongwonkong.withscore.view.common.CommonToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmResults;

/**
 * Created by seongwonkong on 2017. 6. 28..
 */

public class HomeActivity extends BaseActivity {
    public static final String TAG = "HomeActivity";

    @OnClick (R.id.score_storage_btn)
    void loadScores() {
        AppNavigator.goScoreStorageActivity(HomeActivity.this);
    }

    @OnClick (R.id.load_store_btn)
    void loadStoreButtonClick() {
        AppNavigator.goLoadScoreActivity(HomeActivity.this);
    }

    @BindView(R.id.home_score)
    ImageView homeScoreImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

//        animate();
    }

    private void animate() {
        final ObjectAnimator objectAnimator= ObjectAnimator.ofFloat(homeScoreImageView, "translationX", homeScoreImageView.getTranslationX(), homeScoreImageView.getTranslationX() - ViewUtils.convertDpToPx(120));
        objectAnimator.setDuration(10000);
        objectAnimator.setInterpolator(AnimationUtils.loadInterpolator(this, android.R.anim.linear_interpolator));
        objectAnimator.start();
    }

}
