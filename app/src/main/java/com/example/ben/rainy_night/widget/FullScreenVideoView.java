package com.example.ben.rainy_night.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * 自动全屏的VideoView
 *
 * @author Ben
 * @date 2018/3/29
 */
public class FullScreenVideoView extends VideoView {

    public FullScreenVideoView(Context context) {
        super(context);
    }

    public FullScreenVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FullScreenVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //得到默认的大小（0，宽度测量规范）
        int width = getDefaultSize(0, widthMeasureSpec);
        //得到默认的大小（0，高度度测量规范）
        int height = getDefaultSize(0, heightMeasureSpec);
        //设置测量尺寸,将高和宽放进去
        setMeasuredDimension(width, height);
    }
}
