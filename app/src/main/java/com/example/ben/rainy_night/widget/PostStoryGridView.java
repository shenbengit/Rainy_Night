package com.example.ben.rainy_night.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 *
 * @author Ben
 * @date 2018/3/22
 */

public class PostStoryGridView extends GridView {
    public boolean isOnMeasure;

    public PostStoryGridView(Context context) {
        super(context);
    }

    public PostStoryGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public PostStoryGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        isOnMeasure = true;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        isOnMeasure = false;
        super.onLayout(changed, l, t, r, b);
    }
}
