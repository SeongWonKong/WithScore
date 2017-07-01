package com.withscore.seongwonkong.withscore.view.load_score.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.withscore.seongwonkong.withscore.R;

/**
 * Created by seongwonkong on 2017. 7. 1..
 */

public class LoadScoreStepGuide extends LinearLayout {

    private TextView titleTextView;
    private TextView contentTextView;

    public LoadScoreStepGuide(Context context) {
        super(context);
        init();
    }

    public LoadScoreStepGuide(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        getAttrs(attrs);
    }

    public LoadScoreStepGuide(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        getAttrs(attrs, defStyleAttr);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LoadScoreStepGuide);

        setTypeArray(typedArray);
    }


    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LoadScoreStepGuide, defStyle, 0);
        setTypeArray(typedArray);

    }

    private void setTypeArray(TypedArray typedArray) {
        int step = typedArray.getResourceId(R.styleable.LoadScoreStepGuide_step, 0);
        setStep(step);

        typedArray.recycle();
    }

    public void init() {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.component_load_score_step_view, this, false);
        addView(v);

        titleTextView = v.findViewById(R.id.step_title);
        contentTextView = v.findViewById(R.id.step_content);

    }

    private void setStep(final int step) {
        switch (step) {
            default:
            case 1:
                titleTextView.setText(getContext().getText(R.string.load_score_step_1));
                contentTextView.setText(getContext().getText(R.string.load_score_step_1_content));
                break;
            case 2:
                titleTextView.setText(getContext().getText(R.string.load_score_step_2));
                contentTextView.setText(getContext().getText(R.string.load_score_step_2_content));
                break;
        }
    }
}
