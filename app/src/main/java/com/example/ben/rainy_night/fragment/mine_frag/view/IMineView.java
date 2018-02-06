package com.example.ben.rainy_night.fragment.mine_frag.view;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.example.ben.rainy_night.base.BaseView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 *
 * @author Ben
 * @date 2018/1/19
 */

public interface IMineView extends BaseView {
    /**
     *
     * @return FragmentActivity实例
     */
     FragmentActivity getFragmentActivity();
    /**
     *
     * @return img控件
     */
    CircleImageView getHeadImg();

    /**
     *
     * @return username控件
     */
    TextView getTextUser();
}
