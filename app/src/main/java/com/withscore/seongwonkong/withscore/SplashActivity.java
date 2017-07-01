package com.withscore.seongwonkong.withscore;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.withscore.seongwonkong.withscore.application.AppNavigator;
import com.withscore.seongwonkong.withscore.base.BaseActivity;
import com.withscore.seongwonkong.withscore.util.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {
    public static final String TAG = "SplashActivity";

    @BindView(R.id.splash_score)
    ImageView splashScoreImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        goHomeActivity();
    }

    private void goHomeActivity() {
        animate();
    }

    private void animate() {
        ObjectAnimator objectAnimator= ObjectAnimator.ofFloat(splashScoreImageView, "translationX", splashScoreImageView.getTranslationX(), splashScoreImageView.getTranslationX() - ViewUtils.convertDpToPx(50));
        objectAnimator.setDuration(1500);
        objectAnimator.setInterpolator(AnimationUtils.loadInterpolator(this, android.R.anim.accelerate_interpolator));
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                AppNavigator.goHomeActivity(SplashActivity.this);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        objectAnimator.start();
    }
}
