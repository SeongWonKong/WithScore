package com.withscore.seongwonkong.withscore.view.load_score.component;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.withscore.seongwonkong.withscore.R;

/**
 * Created by seongwonkong on 2017. 7. 2..
 */

public class FullSizeGuideView extends LinearLayout {
    public FullSizeGuideView(Context context) {
        super(context);
    }

    public FullSizeGuideView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FullSizeGuideView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.component_load_score_step_view, this, false);
        addView(v);
    }
}
