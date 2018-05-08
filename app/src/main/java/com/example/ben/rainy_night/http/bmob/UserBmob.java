package com.example.ben.rainy_night.http.bmob;

import com.example.ben.rainy_night.http.bmob.entity.UserEntity;
import com.example.ben.rainy_night.event.OnUserEvent;
import com.example.ben.rainy_night.manager.ThreadPoolManager;
import com.example.ben.rainy_night.util.Constant;
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
            UserEntity entity = new UserEntity();
            entity.setUsername(phone);
            entity.setNickName(nickname);
            entity.setSex("保密");
            entity.setMobilePhoneNumber(phone);
            entity.setPassword(password);
            entity.signUp(new SaveListener<UserEntity>() {
                @Override
                public void done(UserEntity entity, BmobException e) {
                    if (e == null) {
                        EventBus.getDefault().post(new OnUserEvent(request, Constant.OK, entity));
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
        Runnable runnable = () -> BmobUser.loginByAccount(account, password, new LogInListener<UserEntity>() {
            @Override
            public void done(UserEntity entity, BmobException e) {
                if (e == null) {
                    EventBus.getDefault().post(new OnUserEvent(request, Constant.OK, entity));
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
        BmobUser.signOrLoginByMobilePhone(phone, code, new LogInListener<UserEntity>() {
            @Override
            public void done(UserEntity bean, BmobException e) {
                if (e == null) {
                    EventBus.getDefault().post(new OnUserEvent(request, Constant.OK, bean));
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
        final UserEntity entity = BmobUser.getCurrentUser(UserEntity.class);
        if (entity == null) {
            EventBus.getDefault().post(new OnUserEvent(request, "暂无用户登陆!", null));
            return;
        }
        Runnable runnable = () -> {
            BmobQuery<UserEntity> query = new BmobQuery<>();
            query.getObject(entity.getObjectId(), new QueryListener<UserEntity>() {
                @Override
                public void done(UserEntity entity, BmobException e) {
                    if (e == null) {
                        EventBus.getDefault().post(new OnUserEvent(request, Constant.OK, entity));
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
     * @param userEntity
     */
    public void updateUser(final String request, final UserEntity userEntity) {
        final UserEntity entity = BmobUser.getCurrentUser(UserEntity.class);
        if (entity == null) {
            EventBus.getDefault().post(new OnUserEvent(request, "暂无用户登陆!", null));
            return;
        }
        Runnable runnable = () -> userEntity.update(entity.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    fetchUserInfo();
                    EventBus.getDefault().post(new OnUserEvent(request, Constant.OK, null));
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
                    EventBus.getDefault().post(new OnUserEvent(request, Constant.OK, null));
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