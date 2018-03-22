package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import android.text.TextUtils;

import com.example.ben.rainy_night.fragment.mine_frag.contract.NickNameContract;
import com.example.ben.rainy_night.fragment.mine_frag.model.UserModel;
import com.example.ben.rainy_night.fragment.mine_frag.model.UserModelImpl;
import com.example.ben.rainy_night.http.bmob.entity.UserEntity;
import com.example.ben.rainy_night.util.Constant;
import com.vondear.rxtools.RxRegTool;

/**
 * @author Ben
 * @date 2018/1/26
 */

public class NickNamePresenterImpl implements NickNameContract.Presenter {

    private NickNameContract.View view;
    private UserModel model;
    private String nickName = "";

    public NickNamePresenterImpl(NickNameContract.View view) {
        this.view = view;
        model = new UserModelImpl();
    }


    /**
     * 修改昵称
     */
    @Override
    public void changeNickName() {
        nickName = view.getEditText().getText().toString().trim();
        String regex = "[A-Za-z0-9_\\u4e00-\\u9fa5]{3,8}";
        if (!RxRegTool.isMatch(regex, nickName)) {
            view.showToast("昵称格式不正确");
            return;
        }

        if (!view.isNetworkAvailable()) {
            view.showToast("当前网络不可用");
            return;
        }

        view.showDialog();
        UserEntity entity = new UserEntity();
        entity.setNickName(nickName);
        model.updateUser(Constant.REQUEST_NICK_NAME, entity);
    }

    /**
     * 修改昵称是否成功
     *
     * @param message
     */
    @Override
    public void isChangeNickNameSuccess(String message) {
        view.cancelDialog();
        if (TextUtils.equals(Constant.OK, message)) {
            view.showToast("昵称更改成功");
        } else {
            view.showToast(message);
        }
    }
}
