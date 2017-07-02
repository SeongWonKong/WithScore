package com.withscore.seongwonkong.withscore.view.load_score;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
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
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.withscore.seongwonkong.withscore.HomeActivity;
import com.withscore.seongwonkong.withscore.R;
import com.withscore.seongwonkong.withscore.WithScoreConstants;
import com.withscore.seongwonkong.withscore.application.AppApplication;
import com.withscore.seongwonkong.withscore.application.AppNavigator;
import com.withscore.seongwonkong.withscore.base.BaseActivity;
import com.withscore.seongwonkong.withscore.realm.WithScoreRealmDb;
import com.withscore.seongwonkong.withscore.realm.WithScoreRealmDbHelper;
import com.withscore.seongwonkong.withscore.util.FileUtils;
import com.withscore.seongwonkong.withscore.util.ViewUtils;
import com.withscore.seongwonkong.withscore.view.common.CommonToast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadScoreActivity extends BaseActivity {
    public static final String TAG = "LoadScoreActivity";

    @BindView(R.id.back_button)
    ImageView mBackButton;

    @BindView(R.id.score_grid_layout)
    GridLayout mGridLayout;

    @BindView(R.id.score_grid_scroll_view)
    ScrollView mScrollView;

    @BindView(R.id.edit_button)
    TextView mEditButton;

    @BindView(R.id.next_button)
    TextView mNextButton;

    private boolean isEditable;
    String imageEncoded;
    List<String> imagesEncodedList;

    private List<Uri> uriList;

    private ValueAnimator mAnimator;
    private AtomicBoolean mIsScrolling = new AtomicBoolean(false);

    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_score);
        ButterKnife.bind(this);

        mInflater = LayoutInflater.from(AppApplication.sApplication);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        init();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

            // When an Image is picked
            if (requestCode == AppNavigator.REQUEST_CODE_SELECT_MULTIPLE_FROM_GALLERY && resultCode == RESULT_OK
                    && null != data) {
                if (uriList == null) {
                    uriList = new ArrayList<>();
                }

                // Get the Image from data

                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                imagesEncodedList = new ArrayList<String>();
                if(data.getData()!=null){

                    Uri mImageUri = data.getData();
                    if (uriList != null && uriList.indexOf(mImageUri) == -1) {
                        uriList.add(mImageUri);
                    } else {
                        new CommonToast(this).make(getString(R.string.load_score_duplicate));
                    }


                    // Get the cursor
                    Cursor cursor = getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded  = cursor.getString(columnIndex);
                    cursor.close();

                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            if (uriList != null && uriList.indexOf(uri) == -1) {
                                uriList.add(uri);
                            } else {
                                new CommonToast(this).make(getString(R.string.load_score_duplicate));
                            }
                            mArrayUri.add(uri);
                            // Get the cursor
                            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded  = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();

                        }
                        Log.v("LOG_TAG", "Selected Images" + uriList.size());
                    }
                }
                initGridLayout();
            } else {
                Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
            }

        super.onActivityResult(requestCode, resultCode, data);
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
                if (isEditable) {
                    mGridLayout.removeViewAt(mGridLayout.getChildCount() - 1);
                    new CommonToast(LoadScoreActivity.this).make(getString(R.string.load_score_edit_toast_message));
                }
                else {
                    final View gridLayoutItem =  mInflater.inflate(R.layout.view_single_score_item, null);
                    addEmptyLoadView(gridLayoutItem);
                }
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> stringUriList = new ArrayList<>();
                for (Uri uri : uriList) {
                    stringUriList.add(uri.toString());
                }

                final long currentTimeStamp = System.currentTimeMillis();

                BitmapDrawable drawable = (BitmapDrawable) ((ImageView)mGridLayout.findViewById(R.id.single_score_image_view)).getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                File file = saveImageFile(stream);

                WithScoreRealmDbHelper.saveScore(currentTimeStamp, file.getAbsolutePath());
                AppNavigator.goScoreCropActivity(LoadScoreActivity.this, stringUriList, currentTimeStamp);
            }
        });

        final View gridLayoutItem =  mInflater.inflate(R.layout.view_single_score_item, null);
        addEmptyLoadView(gridLayoutItem);
    }

    private void initGridLayout() {
        mGridLayout.removeAllViews();
        int size = uriList.size() + 1;


        for (int i = 0 ; i < size ; i++) {
            final View gridLayoutItem =  mInflater.inflate(R.layout.view_single_score_item, null);
            ImageView imageView = (ImageView) gridLayoutItem.findViewById(R.id.single_score_image_view);
            ImageView deleteButton = (ImageView) gridLayoutItem.findViewById(R.id.delete_button);
            if (size - 1 == i) {
                addEmptyLoadView(gridLayoutItem);
                break;
            }

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isEditable) {
                        final int pos = mGridLayout.indexOfChild(gridLayoutItem);
                        mGridLayout.removeView(gridLayoutItem);
                        uriList.remove(pos);
                        checkButtonState();
                    }
                }
            });
            int width = ViewUtils.getDisplayWidthPx(LoadScoreActivity.this) / 3;
            ((RelativeLayout.LayoutParams) imageView.getLayoutParams()).width = width;
            ((RelativeLayout.LayoutParams) imageView.getLayoutParams()).height = (int) (width * 1.414f);
            final int pageNum = i;
            final Uri uri = uriList.get(i);
            imageView.setImageURI(uri);
            gridLayoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isEditable) {
                        AppNavigator.goSingleScoreActivity(LoadScoreActivity.this, uri, uriList.indexOf(uri));
                    }
                }
            });
            gridLayoutItem.setOnLongClickListener(new LongPressListener());
            mGridLayout.addView(gridLayoutItem);
        }

        mGridLayout.setOnDragListener(new DragListener());
    }

    private void addEmptyLoadView(final View gridLayoutItem) {
        gridLayoutItem.setBackgroundResource(R.drawable.load_button_dashed_border);
        View loadMoreButton = gridLayoutItem.findViewById(R.id.single_score_load_more);
        int width = ViewUtils.getDisplayWidthPx(LoadScoreActivity.this) / 3;
        ((RelativeLayout.LayoutParams) loadMoreButton.getLayoutParams()).width = width;
        ((RelativeLayout.LayoutParams) loadMoreButton.getLayoutParams()).height = (int) (width * 1.414f);
        loadMoreButton.setVisibility(View.VISIBLE);
        loadMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppNavigator.goMultiSelectFromGallery(LoadScoreActivity.this);
            }
        });
        mGridLayout.addView(gridLayoutItem);
        checkButtonState();
        return;
    }

    private void checkButtonState() {
        setNextButtonEnabled(mGridLayout.getChildCount() != 1);
        setEditButtonEnabled(mGridLayout.getChildCount() != 1);
    }

    private void enableDeleteButtonInsideGridLayout(final boolean isEnabled) {
        for (int i = 0 ; i < mGridLayout.getChildCount() ; i++) {
            mGridLayout.getChildAt(i).findViewById(R.id.delete_button).setVisibility(isEnabled ? View.VISIBLE : View.GONE);
        }
    }

    private void setEditButtonEnabled(final boolean isEnabled) {
        mEditButton.setEnabled(isEnabled);
        mEditButton.setTextColor(ContextCompat.getColor(this, isEnabled ? android.R.color.white : android.R.color.darker_gray));
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
                    final int pos = mGridLayout.indexOfChild(view);
                    mGridLayout.removeView(view);
                    final Uri uri = uriList.get(pos);
                    uriList.remove(pos);
                    // and push to the new
                    mGridLayout.addView(view, index);
                    uriList.add(index, uri);
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


    private File saveImageFile(ByteArrayOutputStream stream) {
        String fileName = "with_score_" + System.currentTimeMillis();
        File file = FileUtils.getRepoFile(this, WithScoreConstants.WITH_SCORE_DIR_NAME, fileName);
        FileUtils.saveFile(file.getAbsolutePath(), stream);
        return file;
    }
}
