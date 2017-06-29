package com.withscore.seongwonkong.withscore.view.load_score;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.ClipData;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.withscore.seongwonkong.withscore.R;
import com.withscore.seongwonkong.withscore.application.AppApplication;
import com.withscore.seongwonkong.withscore.application.AppNavigator;
import com.withscore.seongwonkong.withscore.base.BaseActivity;
import com.withscore.seongwonkong.withscore.util.ViewUtils;

import java.util.concurrent.atomic.AtomicBoolean;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadScoreActivity extends BaseActivity {
    public static final String TAG = "LoadScoreActivity";

    @BindView(R.id.load_score_tool_bar)
    Toolbar mToolbar;

    @BindView(R.id.score_grid_layout)
    GridLayout mGridLayout;

    @BindView(R.id.score_grid_scroll_view)
    ScrollView mScrollView;

    @BindView(R.id.edit_button)
    TextView mEditButton;

    @BindView(R.id.next_button)
    TextView mNextButton;

    private boolean isEditable;

    private ValueAnimator mAnimator;
    private AtomicBoolean mIsScrolling = new AtomicBoolean(false);

    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_score);
        ButterKnife.bind(this);

        mInflater = LayoutInflater.from(AppApplication.sApplication);
        initToolbar();
        initGridLayout();
        init();
    }

    private void init() {
        isEditable = false;
        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isEditable = !isEditable;
                mEditButton.setText(isEditable ? getString(R.string.load_score_finish_edit_button) : getString(R.string.load_score_edit_button));
                setNextButtonEnabled(!isEditable);
                enableDeleteButtonInsideGridLayout(isEditable);
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.load_score));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initGridLayout() {
        for (int i = 0 ; i < 5 ; i++) {
            final View gridLayoutItem =  mInflater.inflate(R.layout.view_single_score_item, null);
            ImageView imageView = gridLayoutItem.findViewById(R.id.single_score_image_view);
            ImageView deleteButton = gridLayoutItem.findViewById(R.id.delete_button);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isEditable) {
                        mGridLayout.removeView(gridLayoutItem);
                    }
                }
            });
            imageView.setMaxWidth(ViewUtils.getDisplayWidthPx(LoadScoreActivity.this) / 3);
            final int drawableResId = R.drawable.cannon_1 + i;
            imageView.setImageDrawable(getDrawable(drawableResId));
            gridLayoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isEditable) {
                        AppNavigator.goSingleScoreActivity(LoadScoreActivity.this, drawableResId);
                    }
                }
            });
            gridLayoutItem.setOnLongClickListener(new LongPressListener());
            mGridLayout.addView(gridLayoutItem);
        }

        mGridLayout.setOnDragListener(new DragListener());

    }

    private void enableDeleteButtonInsideGridLayout(final boolean isEnabled) {
        for (int i = 0 ; i < mGridLayout.getChildCount() ; i++) {
            mGridLayout.getChildAt(i).findViewById(R.id.delete_button).setVisibility(isEnabled ? View.VISIBLE : View.GONE);
        }
    }

    private void setNextButtonEnabled(final boolean isEnabled) {
        mNextButton.setEnabled(isEnabled);
        mNextButton.setTextColor(ContextCompat.getColor(this, isEnabled ? android.R.color.white : android.R.color.darker_gray));
    }

    class LongPressListener implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View view) {
            if (!isEditable) {
                return false;
            }
            final ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(data, shadowBuilder, view, 0);
            view.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    class DragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            final View view = (View) event.getLocalState();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_LOCATION:
                    // do nothing if hovering above own position
                    if (view == v) return true;
                    // get the new list index
                    final int index = calculateNewIndex(event.getX(), event.getY());

                    final int scrollY = mScrollView.getScrollY();
                    final Rect rect = new Rect();
                    mScrollView.getHitRect(rect);

                    if (event.getY() -  scrollY > mScrollView.getBottom() - 250) {
                        startScrolling(scrollY, mGridLayout.getHeight());
                    } else if (event.getY() - scrollY < mScrollView.getTop() + 250) {
                        startScrolling(scrollY, 0);
                    } else {
                        stopScrolling();
                    }

                    // remove the view from the old position
                    mGridLayout.removeView(view);
                    // and push to the new
                    mGridLayout.addView(view, index);
                    break;
                case DragEvent.ACTION_DROP:
                    view.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    if (!event.getResult()) {
                        view.setVisibility(View.VISIBLE);
                    }
                    break;

            }
            return true;
        }
    }

    private int calculateNewIndex(float x, float y) {
        // calculate which column to move to
        final float cellWidth = mGridLayout.getWidth() / mGridLayout.getColumnCount();
        final int column = (int)(x / cellWidth);

        // calculate which row to move to
        final float cellHeight = mGridLayout.getHeight() / mGridLayout.getRowCount();
        final int row = (int)Math.floor(y / cellHeight);

        // the items in the GridLayout is organized as a wrapping list
        // and not as an actual grid, so this is how to get the new index
        int index = row * mGridLayout.getColumnCount() + column;
        if (index >= mGridLayout.getChildCount()) {
            index = mGridLayout.getChildCount() - 1;
        }

        return index;
    }

    private void startScrolling(int from, int to) {
        if (from != to && mAnimator == null) {
            mIsScrolling.set(true);
            mAnimator = new ValueAnimator();
            mAnimator.setInterpolator(new OvershootInterpolator());
            mAnimator.setDuration(Math.abs(to - from));
            mAnimator.setIntValues(from, to);
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    mScrollView.smoothScrollTo(0, (int) valueAnimator.getAnimatedValue());
                }
            });
            mAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mIsScrolling.set(false);
                    mAnimator = null;
                }
            });
            mAnimator.start();
        }
    }

    private void stopScrolling() {
        if (mAnimator != null) {
            mAnimator.cancel();
        }
    }
}
