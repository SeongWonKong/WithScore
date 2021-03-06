package com.withscore.seongwonkong.withscore.view.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.withscore.seongwonkong.withscore.R;
import com.withscore.seongwonkong.withscore.application.AppApplication;
import com.withscore.seongwonkong.withscore.util.ViewUtils;

/**
 * Created by seongwonkong on 2017. 7. 1..
 */

public class CommonToast extends Toast {
    private Context mContext;
    public CommonToast(Context context) {
        super(context);
        this.mContext = context;
    }

    public void make(final String message) {
        LayoutInflater inflater = (LayoutInflater) AppApplication.sApplication.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.layout_common_toast, null);

        TextView messageTextView = (TextView) v.findViewById(R.id.message);
        messageTextView.setText(message);

        show(this, v, LENGTH_SHORT);
    }

    public void make(final Bitmap bitmap) {
        LayoutInflater inflater = (LayoutInflater) AppApplication.sApplication.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.layout_image_toast, null);

        ImageView imageView = (ImageView) v.findViewById(R.id.toast_image_view);
        imageView.setImageBitmap(bitmap);

        showCenter(this, v, LENGTH_SHORT);
    }

    private void showCenter(Toast toast, View v, int duration) {
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(duration);
        toast.setView(v);
        toast.show();
    }

    private void show(Toast toast, View v, int duration) {
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setDuration(duration);
        toast.setView(v);
        toast.show();
    }
}