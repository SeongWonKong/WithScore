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
import com.withscore.seongwonkong.withscore.application.AppNavigator;
import com.withscore.seongwonkong.withscore.realm.model.Score;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.SimpleFormatter;

import io.realm.RealmResults;

/**
 * Created by seongwonkong on 2017. 7. 3..
 */

public class ScoreStorageRecyclerAdapter extends RecyclerView.Adapter {
    private RealmResults<Score> mItemList;
    private LayoutInflater mInflater;

    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onCardClick(final long createdDateTime);
    }

    public ScoreStorageRecyclerAdapter(RealmResults<Score> scoreList, OnItemClickListener listener) {
        mItemList = scoreList;
        mInflater = LayoutInflater.from(AppApplication.sApplication);
        this.mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SingleItemViewHolder(mInflater.inflate(R.layout.score_storage_single_view, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((SingleItemViewHolder) holder).bind(mItemList.get(position).getThumbnailImagePath(),
                mItemList.get(position).getTitle(),
                mItemList.get(position).getCreatedDateTime(),
                mItemList.get(position).getSeq(),
                mListener);
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public class SingleItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView thumbnailImageView;
        private TextView titleTextView;
        private TextView createdDateTimeTextView;

        public SingleItemViewHolder(View itemView) {
            super(itemView);

            thumbnailImageView = (ImageView) itemView.findViewById(R.id.thumbnail_image_view);
            titleTextView = (TextView) itemView.findViewById(R.id.title_text_view);
            createdDateTimeTextView = (TextView) itemView.findViewById(R.id.created_datetime_text_view);
        }

        public void bind(final String thumbnailUrl, final String title, final long createdDateTime, final long scoreSeq, final OnItemClickListener listener) {
            thumbnailImageView.setImageURI(Uri.parse(thumbnailUrl));
            titleTextView.setText(title);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
            formatter.setTimeZone(TimeZone.getDefault());
            createdDateTimeTextView.setText(formatter.format(new Date(createdDateTime)));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onCardClick(scoreSeq);
                    }
                }
            });
        }
    }
}
