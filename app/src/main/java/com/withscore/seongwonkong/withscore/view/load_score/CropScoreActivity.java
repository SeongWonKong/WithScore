package com.withscore.seongwonkong.withscore.view.load_score;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.withscore.seongwonkong.withscore.R;
import com.withscore.seongwonkong.withscore.WithScoreConstants;
import com.withscore.seongwonkong.withscore.application.AppApplication;
import com.withscore.seongwonkong.withscore.application.AppNavigator;
import com.withscore.seongwonkong.withscore.base.BaseActivity;
import com.withscore.seongwonkong.withscore.realm.WithScoreRealmDbHelper;
import com.withscore.seongwonkong.withscore.util.FileUtils;
import com.withscore.seongwonkong.withscore.util.ViewUtils;
import com.withscore.seongwonkong.withscore.view.common.CommonToast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by seongwonkong on 2017. 7. 2..
 */

public class CropScoreActivity extends BaseActivity {

    @BindView(R.id.back_button)
    ImageView mBackButton;

    @BindView(R.id.crop_image_view)
    CropImageView cropImageView;

    @BindView(R.id.info_text_view)
    TextView mInfoTextView;

    @BindView(R.id.extract_button)
    TextView mExtractButton;

    @BindView(R.id.prev_button)
    TextView mPrevButton;

    @BindView(R.id.next_button)
    TextView mNextButton;

    private LayoutInflater mInflater;
    private int mCurrentPage;
    private ArrayList<String> mStringUrlList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_score);
        ButterKnife.bind(this);

        mInflater = LayoutInflater.from(AppApplication.sApplication);
        startPermissionCheck();
    }

    private void startPermissionCheck() {
        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.MANAGE_DOCUMENTS, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    private void init() {

        Intent intent = getIntent();
        mStringUrlList = new ArrayList<>();
        mStringUrlList.addAll(intent.getStringArrayListExtra("uriList"));

        final long currentTimeStamp = intent.getLongExtra("currentTimeStamp", 0L);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setPagingInformation(0);
        setBottomButton(currentTimeStamp);
    }

    private void setBottomButton(final long currentTimeStamp) {
        mExtractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = cropImageView.getCroppedImage();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                File file = saveImageFile(stream);
                WithScoreRealmDbHelper.saveScorePiece(currentTimeStamp, file.getAbsolutePath());
                new CommonToast(CropScoreActivity.this).make(bitmap);
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentPage != (mStringUrlList.size() - 1)) {
                    setPagingInformation(mCurrentPage + 1);
                } else {
                    AppNavigator.goScoreInfoActivity(CropScoreActivity.this, currentTimeStamp);
                }
            }
        });

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentPage != 0) {
                    setPagingInformation(mCurrentPage - 1);
                }
            }
        });
    }

    private void setPagingInformation(final int currentPageCount) {
        mCurrentPage = currentPageCount;
        mInfoTextView.setText(getString(R.string.crop_score_info, mCurrentPage + 1, mStringUrlList.size()));
        cropImageView.clearAspectRatio();
        cropImageView.clearImage();
        Log.d("KONG", mStringUrlList.get(mCurrentPage) + " " + cropImageView.getChildCount());
        cropImageView.setOnSetImageUriCompleteListener(new CropImageView.OnSetImageUriCompleteListener() {
            @Override
            public void onSetImageUriComplete(CropImageView view, Uri uri, Exception error) {
                if (error != null) {
                    Log.d("KONG", uri.toString() + " " + error.toString());
                }
            }
        });
        cropImageView.setImageUriAsync(Uri.parse(mStringUrlList.get(mCurrentPage)));
        cropImageView.setCropRect(new Rect(0, ViewUtils.convertDpToPx(30), ViewUtils.convertDpToPx(270), ViewUtils.convertDpToPx(90)));
        setPrevNextButtonEnabled();
    }

    private File saveImageFile(ByteArrayOutputStream stream) {
        String fileName = "with_score_" + System.currentTimeMillis();
        File file = FileUtils.getRepoFile(this, WithScoreConstants.WITH_SCORE_DIR_NAME, fileName);
        FileUtils.saveFile(file.getAbsolutePath(), stream);
        return file;
    }

    private void setPrevNextButtonEnabled() {
        if (mCurrentPage == 0) {
            ViewUtils.setEnabled(mPrevButton, false);
            ViewUtils.setEnabled(mNextButton, true);
            mNextButton.setText(mStringUrlList.size() == 1 ? R.string.load_score_next_step : R.string.crop_score_next_score_button);
        }
        else if (mCurrentPage == (mStringUrlList.size() - 1)) {
            ViewUtils.setEnabled(mPrevButton, true);
            ViewUtils.setEnabled(mNextButton, true);
            mNextButton.setText(R.string.load_score_next_step);

        }
        else {
            ViewUtils.setEnabled(mPrevButton, true);
            ViewUtils.setEnabled(mNextButton, true);
            mNextButton.setText(R.string.crop_score_next_score_button);
        }
    }

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            init();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            finish();
        }
    };
}
