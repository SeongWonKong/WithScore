package com.withscore.seongwonkong.withscore.view.load_score;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.withscore.seongwonkong.withscore.R;
import com.withscore.seongwonkong.withscore.base.BaseActivity;
import com.withscore.seongwonkong.withscore.realm.WithScoreRealmDbHelper;
import com.withscore.seongwonkong.withscore.view.common.CommonToast;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by seongwonkong on 2017. 7. 7..
 */

public class ScoreInfoActivity extends BaseActivity {
    @BindView(R.id.back_button)
    ImageView mBackButton;

    @BindView(R.id.score_title_edit_text)
    EditText mScoreTitleEditText;

    @BindView(R.id.complete_button)
    View mCompleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_info);
        ButterKnife.bind(this);

        init(getIntent());
    }


    public void init(Intent intent) {
        if (intent != null) {
            final long currentTimeStamp = intent.getLongExtra("currentTimeStamp", 0L);

            mCompleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mScoreTitleEditText.getText() != null && mScoreTitleEditText.getText().length() > 0) {
                        WithScoreRealmDbHelper.updateScoreTitle(currentTimeStamp, mScoreTitleEditText.getText().toString());
                        new CommonToast(ScoreInfoActivity.this).make(getString(R.string.score_save_success));
                        finish();
                    }
                    else {
                        new CommonToast(ScoreInfoActivity.this).make(getString(R.string.score_save_fail_reason_no_title));
                    }
                }
            });
        }

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
