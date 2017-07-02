package com.withscore.seongwonkong.withscore.view.score_storage;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.withscore.seongwonkong.withscore.R;
import com.withscore.seongwonkong.withscore.base.BaseActivity;
import com.withscore.seongwonkong.withscore.realm.WithScoreRealmDbHelper;
import com.withscore.seongwonkong.withscore.realm.model.Score;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

/**
 * Created by seongwonkong on 2017. 7. 3..
 */

public class ScoreStorageActivity extends BaseActivity {
    @BindView(R.id.back_button)
    ImageView mBackButton;

    @BindView(R.id.title_text)
    TextView mTitleTextView;

    @BindView(R.id.score_storage_recycler_view)
    RecyclerView mRecyclerView;

    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_storage);
        ButterKnife.bind(this);
        init();
    }


    public void init() {
        mTitleTextView.setText(getString(R.string.score_storage_title));
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        RealmResults<Score> scores = WithScoreRealmDbHelper.getScores();

        mRecyclerView.setAdapter(new ScoreStorageRecyclerAdapter(scores));
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }
}
