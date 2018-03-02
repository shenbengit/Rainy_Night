package com.example.ben.rainy_night.bmob;

import com.example.ben.rainy_night.bean.UserBean;
import com.example.ben.rainy_night.fragment.event.OnUserEvent;
import com.example.ben.rainy_night.manager.ThreadPoolManager;
import com.example.ben.rainy_night.util.ConstantUtil;

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
    public void registerUser(final String request, final String phone, final String password) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                UserBean bean = new UserBean();
                bean.setUsername(phone);
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
            }
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
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                BmobUser.loginByAccount(account, password, new LogInListener<UserBean>() {
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
        };
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
     * @param objectId
     */
    public void getUserInformation(final String request, final String objectId) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                BmobQuery<UserBean> query = new BmobQuery<UserBean>();
                query.getObject(objectId, new QueryListener<UserBean>() {
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
        };

        ThreadPoolManager.getInstance().execute(runnable);
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
     * @param request
     * @param userBean
     */
    public void updateUser(final String request, final UserBean userBean) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                userBean.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            EventBus.getDefault().post(new OnUserEvent(request, ConstantUtil.OK, null));
                        } else {
                            EventBus.getDefault().post(new OnUserEvent(request, e.getMessage() + ",ErrorCode:" + e.getErrorCode(), null));
                        }
                    }
                });
            }
        };
        ThreadPoolManager.getInstance().execute(runnable);
    }
}
