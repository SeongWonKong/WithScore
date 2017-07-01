package com.withscore.seongwonkong.withscore.view.load_score;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.withscore.seongwonkong.withscore.R;
import com.withscore.seongwonkong.withscore.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by seongwonkong on 2017. 6. 30..
 */

public class SingleScoreActivity extends BaseActivity {

    @BindView(R.id.back_button)
    ImageView mBackButton;

    @BindView(R.id.single_score_image_view)
    ImageView mSingleScoreImageView;

    @BindView(R.id.title_text)
    TextView mTitleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_score);
        ButterKnife.bind(this);

        init(getIntent());
    }


    public void init(Intent intent) {
        if (intent != null) {
            Uri uri = intent.getParcelableExtra("uri");
            mSingleScoreImageView.setImageURI(uri);

            int pageNum = intent.getIntExtra("pageNum", 0);
            mTitleTextView.setText(getString(R.string.load_score_detail_view, pageNum + 1));
        }

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
