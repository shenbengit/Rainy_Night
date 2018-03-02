package com.example.ben.rainy_night.bmob;

import android.util.Log;

import com.example.ben.rainy_night.bean.PostBean;
import com.example.ben.rainy_night.bean.UserBean;
import com.example.ben.rainy_night.fragment.event.OnPostEvent;
import com.example.ben.rainy_night.manager.ThreadPoolManager;
import com.example.ben.rainy_night.util.ConstantUtil;
import com.example.ben.rainy_night.util.LoggerUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

/**
 * 帖子表增删改查方法封装
 *
 * @author Ben
 * @date 2018/2/22
 */

public class PostBmob {

    private PostBmob() {

    }

    public static PostBmob getInstance() {
        return Holder.bmob;
    }

    private static class Holder {
        private static final PostBmob bmob = new PostBmob();
    }

    /**
     * 发表帖子，不包含图片
     *
     * @param content 帖子内容
     * @param user    帖子作者
     */
    public void publishPost(String content, UserBean user) {
        PostBean post = new PostBean();
        post.setContent(content);
        post.setUser(user);
        post.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    EventBus.getDefault().post(new OnPostEvent(ConstantUtil.OK));
                } else {
                    EventBus.getDefault().post(new OnPostEvent(e.getMessage() + ",ErrorCode: " + e.getErrorCode()));
                }
            }
        });
    }


    /**
     * 发表帖子，包含图片
     *
     * @param paths   帖子图片路径
     * @param content 帖子内容
     * @param user    帖子作者
     */
    public void publishPostWithPicture(final String[] paths, final String content, final UserBean user) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                BmobFile.uploadBatch(paths, new UploadBatchListener() {
                    @Override
                    public void onSuccess(List<BmobFile> list, List<String> list1) {
                        if (list.size() == paths.length) {
                            PostBean post = new PostBean();
                            post.setContent(content);
                            post.setPictures(list);
                            post.setUser(user);
                            post.save(new SaveListener<String>() {
                                @Override
                                public void done(String objectId, BmobException e) {
                                    if (e == null) {
                                        EventBus.getDefault().post(new OnPostEvent(ConstantUtil.OK));
                                    } else {
                                        EventBus.getDefault().post(new OnPostEvent(e.getMessage() + ",ErrorCode: " + e.getErrorCode()));
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onProgress(int i, int i1, int i2, int i3) {
                        Log.e("上传图片", "onProgress: " + i3);
                    }

                    @Override
                    public void onError(int i, String s) {
                        EventBus.getDefault().post(new OnPostEvent("上传至服务器失败: " + s + ",ErrorCode: " + i));
                    }
                });
            }
        };

        ThreadPoolManager.getInstance().execute(runnable);
    }

    /**
     * 查询帖子
     */
    public void queryPost() {
        Runnable runable = new Runnable() {
            @Override
            public void run() {
                BmobQuery<PostBean> query = new BmobQuery<PostBean>();
//        query.addWhereEqualTo("createdAt", "");
//        query.setLimit(10);
//        query.order("createdAt");
//                boolean isCache = query.hasCachedResult(PostBean.class);
//                if (isCache) {
//                    //先从缓存读取，如果没有再从网络获取
//                    query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
//                } else {
//                    //只会从网络获取，同时会在本地缓存数据
//                    query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ONLY);
//                }
//                //表示缓存一天
//                query.setMaxCacheAge(TimeUnit.DAYS.toMillis(1));

                // 希望在查询帖子信息的同时也把发布人的信息查询出来
                query.include("user");
                query.findObjects(new FindListener<PostBean>() {
                    @Override
                    public void done(List<PostBean> list, BmobException e) {
                        if (e == null) {
                            LoggerUtil.e(list.toString());
                            for (int i = 0; i < list.size(); i++) {
                                UserBean bean = list.get(i).getUser();
                                String nickname = bean.getNickName();
                                String headimg = bean.getHeadimg().getUrl();
                            }
                        } else {

                        }
                    }
                });
            }
        };
        ThreadPoolManager.getInstance().execute(runable);

    }

}
