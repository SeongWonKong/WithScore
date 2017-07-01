package com.withscore.seongwonkong.withscore.view.load_score;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.withscore.seongwonkong.withscore.R;
import com.withscore.seongwonkong.withscore.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by seongwonkong on 2017. 6. 30..
 */

public class SingleScoreActivity extends BaseActivity {

    @BindView(R.id.load_score_tool_bar)
    Toolbar mToolbar;

    @BindView(R.id.single_score_image_view)
    ImageView mSingleScoreImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_score);
        ButterKnife.bind(this);

        initToolbar();
        init(getIntent());
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.load_score));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void init(Intent intent) {
        if (intent != null) {
            Uri uri = intent.getParcelableExtra("uri");
            mSingleScoreImageView.setImageURI(uri);
        }
    }
}
