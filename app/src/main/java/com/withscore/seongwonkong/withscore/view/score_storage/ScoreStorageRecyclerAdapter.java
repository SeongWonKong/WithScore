package com.withscore.seongwonkong.withscore.view.score_storage;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.withscore.seongwonkong.withscore.R;
import com.withscore.seongwonkong.withscore.application.AppApplication;
import com.withscore.seongwonkong.withscore.realm.model.Score;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

/**
 * Created by seongwonkong on 2017. 7. 3..
 */

public class ScoreStorageRecyclerAdapter extends RecyclerView.Adapter {
    private RealmResults<Score> mItemList;
    private LayoutInflater mInflater;

    public ScoreStorageRecyclerAdapter(RealmResults<Score> scoreList) {
        mItemList = scoreList;
        mInflater = LayoutInflater.from(AppApplication.sApplication);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SingleItemViewHolder(mInflater.inflate(R.layout.score_storage_single_view, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((SingleItemViewHolder) holder).bind(mItemList.get(position).getThumbnailImagePath(), mItemList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public class SingleItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView thumbnailImageView;
        private TextView titleTextView;

        public SingleItemViewHolder(View itemView) {
            super(itemView);

            thumbnailImageView = (ImageView) itemView.findViewById(R.id.thumbnail_image_view);
            titleTextView = (TextView) itemView.findViewById(R.id.title_text_view);
        }

        public void bind(final String thumbnailUrl, final String title) {
            thumbnailImageView.setImageURI(Uri.parse(thumbnailUrl));
            titleTextView.setText(title);
        }
    }
}
