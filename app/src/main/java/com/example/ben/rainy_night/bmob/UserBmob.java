package com.example.ben.rainy_night.bmob;

import com.example.ben.rainy_night.bean.UserBean;
import com.example.ben.rainy_night.fragment.event.OnUserEvent;
import com.example.ben.rainy_night.util.LoggerUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * @author Ben
 * @date 2018/1/26
 */

public class UserBmob {
    private static final String OK = "ok";

    private UserBmob() {

    }

    public static UserBmob getInstance() {
        return Holder.bmob;
    }

    private static class Holder {
        private static final UserBmob bmob = new UserBmob();

    }

    /**
     * 注册用户
     *
     * @param phone
     * @param password
     */
    public void registerUser(String phone, String password) {
        UserBean bean = new UserBean();
        bean.setUsername(phone);
        bean.setMobilePhoneNumber(phone);
        bean.setPassword(password);
        bean.signUp(new SaveListener<UserBean>() {
            @Override
            public void done(UserBean bean, BmobException e) {
                if (e == null) {
                    EventBus.getDefault().post(new OnUserEvent(OK, bean));
                } else {
                    EventBus.getDefault().post(new OnUserEvent(e.getMessage() + ",ErrorCode:" + e.getErrorCode(), null));
                }

            }
        });
    }

    /**
     * 用户登陆
     *
     * @param account  账号
     * @param password 密码
     */
    public void loginUser(String account, String password) {
        BmobUser.loginByAccount(account, password, new LogInListener<UserBean>() {
            @Override
            public void done(UserBean bean, BmobException e) {
                if (e == null) {
                    EventBus.getDefault().post(new OnUserEvent(OK, bean));
                } else {
                    EventBus.getDefault().post(new OnUserEvent(e.getMessage() + ",ErrorCode:" + e.getErrorCode(), null));
                }
            }
        });
    }

    /**
     * 手机号码一键注册或登录
     *
     * @param phone
     * @param code
     */
    public void signOrLoginByPhone(String phone, String code) {
        BmobUser.signOrLoginByMobilePhone(phone, code, new LogInListener<UserBean>() {
            @Override
            public void done(UserBean bean, BmobException e) {
                if (e == null) {
                    EventBus.getDefault().post(new OnUserEvent(OK, bean));
                } else {
                    EventBus.getDefault().post(new OnUserEvent(e.getMessage() + ",ErrorCode:" + e.getErrorCode(), null));
                }
            }
        });
    }

    /**
     * 获取用户信息
     *
     * @param objectId
     */
    public void getUserInformation(String objectId) {
        BmobQuery<UserBean> query = new BmobQuery<UserBean>();
        query.getObject(objectId, new QueryListener<UserBean>() {
            @Override
            public void done(UserBean bean, BmobException e) {
                if (e == null) {
                    LoggerUtil.d(bean);
                    EventBus.getDefault().post(new OnUserEvent(OK, bean));
                } else {
                    EventBus.getDefault().post(new OnUserEvent(e.getMessage() + ",ErrorCode:" + e.getErrorCode(), null));
                }
            }
        });
    }

    public void getUserInformationCache(String objectId) {
        BmobQuery<UserBean> query = new BmobQuery<UserBean>();
        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        query.findObjects(new FindListener<UserBean>() {
            @Override
            public void done(List<UserBean> list, BmobException e) {

            }
        });
    }

    /**
     * 更新用户信息
     *
     * @param userBean
     */
    public void updateUser(UserBean userBean) {
        UserBean bean = userBean;
        bean.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    EventBus.getDefault().post(new OnUserEvent(OK, null));
                } else {
                    EventBus.getDefault().post(new OnUserEvent(e.getMessage() + ",ErrorCode:" + e.getErrorCode(), null));
                }
            }
        });
    }
}
