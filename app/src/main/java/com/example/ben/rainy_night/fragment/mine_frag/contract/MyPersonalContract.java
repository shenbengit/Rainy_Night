package com.example.ben.rainy_night.fragment.mine_frag.contract;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.example.ben.rainy_night.base.BasePresenter;
import com.example.ben.rainy_night.base.BaseView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 *
 * @author Ben
 * @date 2018/3/22
 */

public interface MyPersonalContract {

    interface View extends BaseView {

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
         * @return sex控件
         */
        TextView getTextSex();

        /**
         * @return birthday控件
         */
        TextView getTextBirthday();

    }

    interface Presenter extends BasePresenter {
        /**
         * 选择照片返回的数据
         *
         * @param requestCode 请求码
         * @param resultCode  结果码
         * @param data        数据
         */
        void onActivityResult(int requestCode, int resultCode, Intent data);

        /**
         * 更新用户信息
         */
        void updateUser();

        /**
         * 更新用户信息是否成功
         *
         * @param message 成功：“ok”,失败：“e.getMessage()”
         */
        void isUpdateUserSuccess(String message);
    }
}
