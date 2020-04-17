package com.soundking.flodlabelsview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.List;

/**
 * 作者：孙贤武 on 2020/4/14 09:54
 */
public class FoldLabelView extends LinearLayout {

    private LabelsView mLabelsView;
    private ImageView mIv;
    private RelativeLayout mRl;
    private int mLabelsHeight;
    private int mLineHeight;
    private ValueAnimator mAnimator;
    private boolean isFold = false;
    private long mAniDuration = 250;
    private int mShowCount = 2;

    public FoldLabelView(Context context) {
        this(context, null);
    }

    public FoldLabelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FoldLabelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.fold_labelsview_layout, this);

        mLabelsView = findViewById(R.id.view_labels);
        mIv = findViewById(R.id.iv_arrow);
        mRl = findViewById(R.id.rl_arrow);

        mRl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLabelsHeight == 0) {
                    return;
                }
                if (mAnimator != null && mAnimator.isRunning()) {
                    return;
                }
                int start = mLabelsHeight;
                int end = mLineHeight * mShowCount + mLabelsView.getPaddingTop() + mLabelsView.getPaddingBottom()
                        + mLabelsView.getLineMargin() * (mShowCount - 1);
                int middle;

                ViewPropertyAnimator animator = mIv.animate().setDuration(mAniDuration);
                if (isFold) {
                    middle = start;
                    start = end;
                    end = middle;
                    animator.rotation(180);
                } else {
                    animator.rotation(0);
                }
                animator.start();

                mAnimator = ObjectAnimator.ofInt(start, end);
                mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value = (int) animation.getAnimatedValue();

                        ViewGroup.LayoutParams layoutParams = mLabelsView.getLayoutParams();
                        layoutParams.height = value;
                        mLabelsView.requestLayout();
                    }
                });
                mAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        isFold = !isFold;
                    }
                });
                mAnimator.setDuration(mAniDuration);
                mAnimator.start();

            }
        });
    }

    public void setOnLabelClickListener(LabelsView.OnLabelClickListener l) {
        mLabelsView.setOnLabelClickListener(l);
    }

    public int getShowCount() {
        return mShowCount;
    }

    public long getAniDuration() {
        return mAniDuration;
    }

    public void setAniDuration(long aniDuration) {
        mAniDuration = aniDuration;
    }

    /**
     * 设置标签列表：最大显示行数默认为2行
     *
     * @param labels
     */
    public void setLabels(List<String> labels) {
        setLabels(labels, 2);
    }

    /**
     * 设置标签列表
     *
     * @param labels
     * @param showCount 最大显示行数
     */
    public void setLabels(List<String> labels, int showCount) {
        this.mShowCount = showCount;
        mLabelsView.setLabels(labels);
        mLabelsView.post(new Runnable() {
            @Override
            public void run() {
                int linecount = mLabelsView.getLinecount();
                mLabelsHeight = mLabelsView.getMeasuredHeight();
                mLineHeight = (mLabelsHeight - mLabelsView.getPaddingTop() - mLabelsView.getPaddingBottom()
                        - (linecount - 1) * mLabelsView.getLineMargin()) / linecount;

                if (linecount > mShowCount) {
                    isFold = true;
                    mRl.setVisibility(VISIBLE);
                    ViewGroup.LayoutParams layoutParams = mLabelsView.getLayoutParams();
                    layoutParams.height = mLineHeight * mShowCount + getPaddingTop() + getPaddingBottom()
                            + mLabelsView.getLineMargin() * (mShowCount - 1);
                    mLabelsView.requestLayout();
                } else {
                    mRl.setVisibility(INVISIBLE);
                }
            }
        });
    }
}
