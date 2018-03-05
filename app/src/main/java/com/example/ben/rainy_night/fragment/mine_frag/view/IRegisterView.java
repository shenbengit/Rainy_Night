package com.example.ben.rainy_night.fragment.mine_frag.view;

import android.widget.TextView;

import com.chaychan.viewlib.PowerfulEditText;
import com.example.ben.rainy_night.base.BaseView;

/**
 * @author Ben
 * @date 2018/1/22
 */

public interface IRegisterView extends BaseView {

    /**
     * @return 手机号
     */
    PowerfulEditText getEditPhone();

    /**
     * @return 用户名
     */
    PowerfulEditText getEditName();

    /**
     * @return 密码
     */
    PowerfulEditText getEditPassWord();

    /**
     * @return 图片验证码
     */
    PowerfulEditText getEditPictureCode();

    /**
     * @return 手机验证码
     */
    PowerfulEditText getEditPhoneCode();

    /**
     * 发送手机验证码
     *
     * @return
     */
    TextView getTextPhoneCode();

    /**
     * 图片验证码
     *
     * @return
     */
    String getStringPictureCode();
}
