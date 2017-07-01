package com.withscore.seongwonkong.withscore.application;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.withscore.seongwonkong.withscore.HomeActivity;
import com.withscore.seongwonkong.withscore.R;
import com.withscore.seongwonkong.withscore.view.load_score.LoadScoreActivity;
import com.withscore.seongwonkong.withscore.view.load_score.SingleScoreActivity;

/**
 * Created by seongwonkong on 2017. 6. 28..
 */

public class AppNavigator {

    public static final int REQUEST_CODE_SELECT_MULTIPLE_FROM_GALLERY = 89;
    public static void goHomeActivity(Activity activity) {
        Intent intent = new Intent(activity, HomeActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void goLoadScoreActivity(Activity activity) {
        Intent intent = new Intent(activity, LoadScoreActivity.class);
        activity.startActivity(intent);
    }

    public static void goSingleScoreActivity(Activity activity, Uri uri, int pageNum) {
        Intent intent = new Intent(activity, SingleScoreActivity.class);
        intent.putExtra("uri", uri);
        intent.putExtra("pageNum", pageNum);
        activity.startActivity(intent);
    }

    public static void goMultiSelectFromGallery(Activity activity) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(Intent.createChooser(intent, AppApplication.sApplication.getString(R.string.select_from_gallery)), REQUEST_CODE_SELECT_MULTIPLE_FROM_GALLERY);
    }
}
