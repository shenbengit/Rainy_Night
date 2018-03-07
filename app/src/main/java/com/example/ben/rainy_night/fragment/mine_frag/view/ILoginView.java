package com.example.ben.rainy_night.fragment.mine_frag.view;

import com.chaychan.viewlib.PowerfulEditText;
import com.example.ben.rainy_night.base.BaseView;

/**
 *
 * @author Ben
 * @date 2018/1/22
 */

public interface ILoginView extends BaseView {

    /**
     * @return 手机号
     */
    PowerfulEditText getEditPhone();

    /**
     * @return 密码
     */
    PowerfulEditText getEditPassWord();

    /**
     * 保存到 SP
     * @param key
     * @param value
     */
    void putSpValue(String key,String value);
}
