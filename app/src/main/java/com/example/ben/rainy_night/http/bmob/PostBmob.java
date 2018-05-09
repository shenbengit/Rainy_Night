package com.example.ben.rainy_night.http.bmob;

import com.example.ben.rainy_night.event.OnPostCommentEvent;
import com.example.ben.rainy_night.event.OnPostEvent;
import com.example.ben.rainy_night.event.OnPostLikesEvent;
import com.example.ben.rainy_night.http.bmob.entity.CommentEntity;
import com.example.ben.rainy_night.http.bmob.entity.PostEntity;
import com.example.ben.rainy_night.http.bmob.entity.UserEntity;
import com.example.ben.rainy_night.manager.ThreadPoolManager;
import com.example.ben.rainy_night.util.Constant;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadBatchListener;

/**
 * 帖子表增删改查方法封装
 * 帖子评论，喜欢帖子
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
    public void publishPost(String content, UserEntity user) {
        Runnable runnable = () -> {
            PostEntity post = new PostEntity();
            post.setContent(content);
            post.setUser(user);
            post.save(new SaveListener<String>() {
                @Override
                public void done(String objectId, BmobException e) {
                    if (e == null) {
                        EventBus.getDefault().post(new OnPostEvent(Constant.OK));
                    } else {
                        EventBus.getDefault().post(new OnPostEvent(e.getMessage() + ",ErrorCode: " + e.getErrorCode()));
                    }
                }
            });
        };
        ThreadPoolManager.getInstance().execute(runnable);
    }


    /**
     * 发表帖子，包含图片
     *
     * @param paths   帖子图片路径
     * @param content 帖子内容
     * @param user    帖子作者
     */
    public void publishPostWithPicture(final String[] paths, final String content, final UserEntity user) {
        Runnable runnable = () -> BmobFile.uploadBatch(paths, new UploadBatchListener() {
            @Override
            public void onSuccess(List<BmobFile> list, List<String> list1) {
                if (list.size() == paths.length) {
                    PostEntity post = new PostEntity();
                    post.setContent(content);
                    post.setPictures(list);
                    post.setUser(user);
                    post.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {
                            if (e == null) {
                                EventBus.getDefault().post(new OnPostEvent(Constant.OK));
                            } else {
                                EventBus.getDefault().post(new OnPostEvent(e.getMessage() + ",ErrorCode: " + e.getErrorCode()));
                            }
                        }
                    });
                }
            }

            @Override
            public void onProgress(int i, int i1, int i2, int i3) {

            }

            @Override
            public void onError(int i, String s) {
                EventBus.getDefault().post(new OnPostEvent("上传至服务器失败: " + s + ",ErrorCode: " + i));
            }
        });

        ThreadPoolManager.getInstance().execute(runnable);
    }

    /**
     * 查询帖子
     *
     * @param action    用来区别下拉刷新、上拉加载
     * @param createdAt 根据创建时间查询
     */
    public void queryPost(String action, String createdAt) {
        Runnable runnable = () -> {
            BmobQuery<PostEntity> query = new BmobQuery<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            Date date = null;
            try {
                date = sdf.parse(createdAt);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            query.addWhereLessThan("createdAt", new BmobDate(date));
            query.order("-createdAt");
            query.include("user");
            query.setLimit(10);
            query.findObjects(new FindListener<PostEntity>() {
                @Override
                public void done(List<PostEntity> list, BmobException e) {
                    if (e == null) {
                        EventBus.getDefault().post(new OnPostEvent(Constant.OK, action, list));
                    } else {
                        EventBus.getDefault().post(new OnPostEvent(e.getMessage() + ",ErrorCode: " + e.getErrorCode(), action));
                    }
                }
            });
        };
        ThreadPoolManager.getInstance().execute(runnable);
    }

    /**
     * 给某条帖子添加评论
     *
     * @param objectId 帖子的objectId
     * @param content  评论帖子的内容
     */
    public void addPostComment(String objectId, String content) {
        Runnable runnable = () -> {
            UserEntity user = BmobUser.getCurrentUser(UserEntity.class);
            PostEntity post = new PostEntity();
            post.setObjectId(objectId);
            CommentEntity comment = new CommentEntity();
            comment.setContent(content);
            comment.setPost(post);
            comment.setUser(user);
            comment.save(new SaveListener<String>() {
                @Override
                public void done(String objectId1, BmobException e) {
                    if (e == null) {
                        EventBus.getDefault().post(new OnPostCommentEvent(Constant.ACTION_ADD, Constant.OK));
                    } else {
                        EventBus.getDefault().post(new OnPostCommentEvent(Constant.ACTION_ADD, e.getMessage() + ",ErrorCode: " + e.getErrorCode()));
                    }
                }
            });
        };
        ThreadPoolManager.getInstance().execute(runnable);
    }


    /**
     * 删除帖子的评论
     *
     * @param objectId 评论的objectId
     */
    public void removePostComment(String objectId) {
        Runnable runnable = () -> {
            CommentEntity comment = new CommentEntity();
            comment.delete(objectId, new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        EventBus.getDefault().post(new OnPostCommentEvent(Constant.ACTION_REMOVE, Constant.OK));
                    } else {
                        EventBus.getDefault().post(new OnPostCommentEvent(Constant.ACTION_REMOVE, e.getMessage() + ",ErrorCode: " + e.getErrorCode()));
                    }
                }
            });
        };
        ThreadPoolManager.getInstance().execute(runnable);
    }

    /**
     * 查询某条帖子的所有评论
     *
     * @param objectId 帖子的objectId
     */
    public void queryPostComment(String objectId) {
        Runnable runnable = () -> {
            BmobQuery<CommentEntity> query = new BmobQuery<>();
            PostEntity post = new PostEntity();
            post.setObjectId(objectId);
            query.addWhereEqualTo("post", new BmobPointer(post));
            query.include("user,post.author");
            query.findObjects(new FindListener<CommentEntity>() {
                @Override
                public void done(List<CommentEntity> list, BmobException e) {
                    if (e == null) {
                        EventBus.getDefault().post(new OnPostCommentEvent(Constant.ACTION_QUERY, list, Constant.OK));
                    } else {
                        EventBus.getDefault().post(new OnPostCommentEvent(Constant.ACTION_QUERY, e.getMessage() + ",ErrorCode: " + e.getErrorCode()));
                    }
                }
            });
        };
        ThreadPoolManager.getInstance().execute(runnable);
    }

    /**
     * 给某条帖子“点赞”
     *
     * @param objectId 帖子的objectId
     */
    public void addPostLikes(String objectId) {
        Runnable runnable = () -> {
            UserEntity user = BmobUser.getCurrentUser(UserEntity.class);
            PostEntity post = new PostEntity();
            post.setObjectId(objectId);
            BmobRelation relation = new BmobRelation();
            relation.add(user);
            post.setLikes(relation);
            post.update(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        EventBus.getDefault().post(new OnPostLikesEvent(Constant.ACTION_ADD, Constant.OK));
                    } else {
                        EventBus.getDefault().post(new OnPostLikesEvent(Constant.ACTION_ADD, e.getMessage() + ",ErrorCode: " + e.getErrorCode()));
                    }
                }
            });
        };
        ThreadPoolManager.getInstance().execute(runnable);
    }

    /**
     * 给某条帖子取消“取消点赞”
     *
     * @param objectId 帖子的objectId
     */
    public void removePostLikes(String objectId) {
        Runnable runnable = () -> {
            PostEntity post = new PostEntity();
            UserEntity user = BmobUser.getCurrentUser(UserEntity.class);
            post.setObjectId(objectId);
            BmobRelation relation = new BmobRelation();
            relation.remove(user);
            post.setLikes(relation);
            post.update(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        EventBus.getDefault().post(new OnPostLikesEvent(Constant.ACTION_REMOVE, Constant.OK));
                    } else {
                        EventBus.getDefault().post(new OnPostLikesEvent(Constant.ACTION_REMOVE, e.getMessage() + ",ErrorCode: " + e.getErrorCode()));
                    }
                }
            });
        };
        ThreadPoolManager.getInstance().execute(runnable);
    }

    /**
     * 查询喜欢某条帖子的人数
     *
     * @param objectId 帖子的objectId
     */
    public void queryPostLikes(String objectId) {
        Runnable runnable = () -> {
            BmobQuery<UserEntity> query = new BmobQuery<>();
            PostEntity post = new PostEntity();
            post.setObjectId(objectId);
            query.addWhereRelatedTo("likes", new BmobPointer(post));
            query.findObjects(new FindListener<UserEntity>() {
                @Override
                public void done(List<UserEntity> list, BmobException e) {
                    if (e == null) {
                        EventBus.getDefault().post(new OnPostLikesEvent(Constant.ACTION_REMOVE, list, Constant.OK));
                    } else {
                        EventBus.getDefault().post(new OnPostLikesEvent(Constant.ACTION_REMOVE, e.getMessage() + ",ErrorCode: " + e.getErrorCode()));
                    }
                }
            });
        };
        ThreadPoolManager.getInstance().execute(runnable);
    }

}