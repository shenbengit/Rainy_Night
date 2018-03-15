package com.example.ben.rainy_night.bmob;

import com.example.ben.rainy_night.bean.UserBean;
import com.example.ben.rainy_night.fragment.event.OnUserEvent;
import com.example.ben.rainy_night.manager.ThreadPoolManager;
import com.example.ben.rainy_night.util.ConstantUtil;
import com.example.ben.rainy_night.util.LoggerUtil;

import org.greenrobot.eventbus.EventBus;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * User信息表增删改查方法封装
 *
 * @author Ben
 * @date 2018/1/26
 */

public class UserBmob {

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
     * @param request
     * @param phone
     * @param password
     */
    public void registerUser(final String request, final String phone, final String nickname, final String password) {
        Runnable runnable = () -> {
            UserBean bean = new UserBean();
            bean.setUsername(phone);
            bean.setNickName(nickname);
            bean.setSex("保密");
            bean.setMobilePhoneNumber(phone);
            bean.setPassword(password);
            bean.signUp(new SaveListener<UserBean>() {
                @Override
                public void done(UserBean bean, BmobException e) {
                    if (e == null) {
                        EventBus.getDefault().post(new OnUserEvent(request, ConstantUtil.OK, bean));
                    } else {
                        EventBus.getDefault().post(new OnUserEvent(request, e.getMessage() + ",ErrorCode:" + e.getErrorCode(), null));
                    }

                }
            });
        };
        ThreadPoolManager.getInstance().execute(runnable);
    }

    /**
     * 用户登陆
     *
     * @param request
     * @param account  账号
     * @param password 密码
     */
    public void loginUser(final String request, final String account, final String password) {
        Runnable runnable = () -> BmobUser.loginByAccount(account, password, new LogInListener<UserBean>() {
            @Override
            public void done(UserBean bean, BmobException e) {
                if (e == null) {
                    EventBus.getDefault().post(new OnUserEvent(request, ConstantUtil.OK, bean));
                } else {
                    EventBus.getDefault().post(new OnUserEvent(request, e.getMessage() + ",ErrorCode:" + e.getErrorCode(), null));
                }
            }
        });
        ThreadPoolManager.getInstance().execute(runnable);

    }

    /**
     * 手机号码一键注册或登录
     *
     * @param request
     * @param phone
     * @param code
     */
    public void signOrLoginByPhone(final String request, String phone, String code) {
        BmobUser.signOrLoginByMobilePhone(phone, code, new LogInListener<UserBean>() {
            @Override
            public void done(UserBean bean, BmobException e) {
                if (e == null) {
                    EventBus.getDefault().post(new OnUserEvent(request, ConstantUtil.OK, bean));
                } else {
                    EventBus.getDefault().post(new OnUserEvent(request, e.getMessage() + ",ErrorCode:" + e.getErrorCode(), null));
                }
            }
        });
    }

    /**
     * 获取用户信息
     *
     * @param request
     */
    public void getUserInformation(final String request) {
        final UserBean bean = BmobUser.getCurrentUser(UserBean.class);
        if (bean == null) {
            EventBus.getDefault().post(new OnUserEvent(request, "暂无用户登陆!", null));
            return;
        }

        Runnable runnable = () -> {
            BmobQuery<UserBean> query = new BmobQuery<>();
            query.getObject(bean.getObjectId(), new QueryListener<UserBean>() {
                @Override
                public void done(UserBean bean1, BmobException e) {
                    if (e == null) {
                        EventBus.getDefault().post(new OnUserEvent(request, ConstantUtil.OK, bean1));
                    } else {
                        EventBus.getDefault().post(new OnUserEvent(request, e.getMessage() + ",ErrorCode:" + e.getErrorCode(), null));
                    }
                }
            });
        };

        ThreadPoolManager.getInstance().execute(runnable);
    }

    /**
     * 更新用户信息
     *
     * @param request
     * @param userBean
     */
    public void updateUser(final String request, final UserBean userBean) {
        final UserBean bean = BmobUser.getCurrentUser(UserBean.class);
        if (bean == null) {
            EventBus.getDefault().post(new OnUserEvent(request, "暂无用户登陆!", null));
            return;
        }
        Runnable runnable = () -> userBean.update(bean.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    fetchUserInfo();
                    EventBus.getDefault().post(new OnUserEvent(request, ConstantUtil.OK, null));
                } else {
                    EventBus.getDefault().post(new OnUserEvent(request, e.getMessage() + ",ErrorCode:" + e.getErrorCode(), null));
                }
            }
        });
        ThreadPoolManager.getInstance().execute(runnable);
    }

    /**
     * 用户更新账号密码
     *
     * @param request
     * @param oldPwd
     * @param newPwd
     */
    public void updateCurrentUserPassword(String request, final String oldPwd, final String newPwd) {
        Runnable runnable = () -> BmobUser.updateCurrentUserPassword(oldPwd, newPwd, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    EventBus.getDefault().post(new OnUserEvent(request, ConstantUtil.OK, null));
                } else {
                    EventBus.getDefault().post(new OnUserEvent(request, e.getMessage() + ",ErrorCode:" + e.getErrorCode(), null));
                }
            }
        });
        ThreadPoolManager.getInstance().execute(runnable);
    }

    /**
     * 同步本地缓存的用户信息
     * 注意：需要先登录，否则会报9024错误
     */
    private void fetchUserInfo() {
        BmobUser.fetchUserJsonInfo(new FetchUserInfoListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    LoggerUtil.e("同步本地缓存的用户信息: " + s);
                }
            }
        });
    }
}