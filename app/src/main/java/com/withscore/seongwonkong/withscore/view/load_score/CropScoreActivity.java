package com.withscore.seongwonkong.withscore.view.load_score;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.theartofdev.edmodo.cropper.CropImageView;
import com.withscore.seongwonkong.withscore.R;
import com.withscore.seongwonkong.withscore.WithScoreConstants;
import com.withscore.seongwonkong.withscore.application.AppApplication;
import com.withscore.seongwonkong.withscore.base.BaseActivity;
import com.withscore.seongwonkong.withscore.realm.WithScoreRealmDbHelper;
import com.withscore.seongwonkong.withscore.util.FileUtils;
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
    private int currentPageCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_score);
        ButterKnife.bind(this);

        mInflater = LayoutInflater.from(AppApplication.sApplication);


        init();
    }

    private void init() {
        currentPageCount = 0;

        Intent intent = getIntent();
        ArrayList<String> stringUriList = new ArrayList<>();
        stringUriList.addAll(intent.getStringArrayListExtra("uriList"));

        final long currentTimeStamp = intent.getLongExtra("currentTimeStamp", 0L);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mInfoTextView.setText(getString(R.string.crop_score_info, currentPageCount + 1, stringUriList.size()));

        cropImageView.setImageUriAsync(Uri.parse(stringUriList.get(0)));
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
    }

    private File saveImageFile(ByteArrayOutputStream stream) {
        String fileName = "with_score_" + System.currentTimeMillis();
        File file = FileUtils.getRepoFile(this, WithScoreConstants.WITH_SCORE_DIR_NAME, fileName);
        FileUtils.saveFile(file.getAbsolutePath(), stream);
        return file;
    }
}
