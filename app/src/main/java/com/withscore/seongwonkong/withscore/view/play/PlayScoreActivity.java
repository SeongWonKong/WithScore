package com.withscore.seongwonkong.withscore.view.play;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.withscore.seongwonkong.withscore.R;
import com.withscore.seongwonkong.withscore.base.BaseActivity;
import com.withscore.seongwonkong.withscore.realm.WithScoreRealmDbHelper;
import com.withscore.seongwonkong.withscore.realm.model.Score;
import com.withscore.seongwonkong.withscore.realm.model.ScorePiece;
import com.withscore.seongwonkong.withscore.view.score_storage.ScoreStorageRecyclerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

/**
 * Created by seongwonkong on 2017. 7. 8..
 */

public class PlayScoreActivity extends BaseActivity {

    @BindView(R.id.play_score_linear_layout)
    LinearLayout mLinearLayout;

    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_score);
        ButterKnife.bind(this);

        mInflater = LayoutInflater.from(PlayScoreActivity.this);
        init(getIntent());
    }


    public void init(final Intent intent) {
        if (intent == null) return;

        final long createdDateTime = intent.getLongExtra("createdDateTime", 0L);
        Log.d("KONG", createdDateTime + " createdDateTime");
        List<ScorePiece> scorePieces = WithScoreRealmDbHelper.getScorePieces(createdDateTime);
        Log.d("KONG", scorePieces.size() + " SIZE");
        initScores(scorePieces);
    }


    private void initScores(List<ScorePiece> scorePieces) {
        mLinearLayout.removeAllViews();

        for (ScorePiece scorePiece : scorePieces) {
            Log.d("KONG", scorePiece.getImagePath() + " ");
            View view = mInflater.inflate(R.layout.layout_play_score_single_content, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.play_score_single_image_view);
            imageView.setImageURI(Uri.parse(scorePiece.getImagePath()));
            mLinearLayout.addView(view);
        }
    }

}
