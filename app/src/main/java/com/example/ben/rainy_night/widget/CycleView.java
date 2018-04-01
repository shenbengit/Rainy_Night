package com.example.ben.rainy_night.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.ben.rainy_night.R;


/**
 * @author Ben
 * @date 2018/3/29
 */
public class CycleView extends View {

    public CycleView(Context context) {
        super(context);
    }


    public CycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CycleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public CycleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
