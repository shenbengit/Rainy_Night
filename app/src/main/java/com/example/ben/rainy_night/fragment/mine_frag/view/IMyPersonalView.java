package com.example.ben.rainy_night.fragment.mine_frag.view;

import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.example.ben.rainy_night.base.BaseView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Ben
 * @date 2018/1/19
 */

public interface IMyPersonalView extends BaseView {

    /**
     * 获取Activity
     *
     * @return
     */
    FragmentActivity getFragAct();

    /**
     * @return img控件
     */
    CircleImageView getHeadImg();

    /**
     * @return username控件
     */
    TextView getTextUser();

    /**
     * @return sex控件
     */
    TextView getTextSex();

    /**
     * @return birthday控件
     */
    TextView getTextBirthday();

    /**
     * @return email控件
     */
    TextView getTextEmail();
}
