package com.example.ben.rainy_night.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.util.LoggerUtil;
import com.example.ben.rainy_night.util.ToastUtil;
import com.luck.picture.lib.widget.PreviewViewPager;
import com.vondear.rxtools.view.dialog.RxDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;

/**
 * 重写RxDialog,用于全屏显示一张图片的Dialog
 *
 * @author Ben
 * @date 2018/2/26
 */

public class EnlargePictureDialog extends RxDialog {

    private PreviewViewPager mViewPager;
    private ImageView mImage;
    private TextView mTitle;
    private ImageButton mDelete;

    private OnDeletePictureOnClickListener mListenerDelete;

    private SavePictureDialog mDialog;

    /**
     * 是否是显示多张图片
     */
    private boolean isShowManyPicture = false;
    /**
     * 是否可以删除图片的图片，
     * true为是，false为否（为true时，显示删除图片的按钮）
     * 默认为false
     */
    private boolean isCanDeletePicture = false;

    public EnlargePictureDialog(Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }


    public EnlargePictureDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public EnlargePictureDialog(Context context) {
        super(context);
    }

    public EnlargePictureDialog(Context context, float alpha, int gravity) {
        super(context, alpha, gravity);
    }

    public void setIsDeleteListener(OnDeletePictureOnClickListener listener) {
        this.mListenerDelete = listener;
    }

    public void setImagePath(String filePath) {
        this.isShowManyPicture = false;
        initView();
        GlideApp.with(mContext).load(filePath).error(R.mipmap.img_picture_load_failed).into(mImage);
    }

    public void setImageUri(Uri uri) {
        this.isShowManyPicture = false;
        initView();
        GlideApp.with(mContext).load(uri).error(R.mipmap.img_picture_load_failed).into(mImage);
    }

    public void setImageRes(int resId) {
        this.isShowManyPicture = false;
        initView();
        mImage.setImageResource(resId);
    }

    public void setImageBitmap(Bitmap bitmap) {
        this.isShowManyPicture = false;
        initView();
        GlideApp.with(mContext).load(bitmap).error(R.mipmap.img_picture_load_failed).into(mImage);
    }

    public <T> void setImageList(List<T> images, int position, boolean isCanDeletePicture) {
        this.isShowManyPicture = true;
        this.isCanDeletePicture = isCanDeletePicture;
        initView();
        initViewPager(images, position);
    }

    /**
     * 初始化
     */
    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_enlarge_picture, null);
        ImageButton mBack = view.findViewById(R.id.ib_left_back);
        mTitle = view.findViewById(R.id.tv_picture_title);
        mDelete = view.findViewById(R.id.ib_delete);
        mViewPager = view.findViewById(R.id.pv_previewPager);
        mImage = view.findViewById(R.id.iv_enlarge_picture);
        if (isShowManyPicture) {
            mTitle.setVisibility(View.VISIBLE);
            if (isCanDeletePicture) {
                mDelete.setVisibility(View.VISIBLE);
            } else {
                mDelete.setVisibility(View.GONE);
            }
            mViewPager.setVisibility(View.VISIBLE);
            mImage.setVisibility(View.GONE);

        } else {
            mTitle.setVisibility(View.GONE);
            mDelete.setVisibility(View.GONE);
            mViewPager.setVisibility(View.GONE);
            mImage.setVisibility(View.VISIBLE);

        }
        mBack.setOnClickListener(v -> cancel());
        setFullScreen();
        setContentView(view);
    }

    /**
     * 设置viewpager
     *
     * @param images
     * @param position
     */
    private <T> void initViewPager(final List<T> images, int position) {
        final int[] index = {position};
        mTitle.setText((position + 1) + "/" + images.size());
        SimpleFragmentAdapter<T> mAdapter = new SimpleFragmentAdapter<>(images);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(position);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                index[0] = position;
                mTitle.setText((position + 1) + "/" + images.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage("确定删除这张图片吗？")
                    .setPositiveButton("确定", (dialog, which) -> {
                        if (mListenerDelete == null) {
                            ToastUtil.show(mContext, "删除失败");
                            dialog.cancel();
                            cancel();
                            return;
                        }
                        mListenerDelete.isDeleteListener(index[0]);
                        dialog.cancel();
                        cancel();
                    })
                    .setNegativeButton("取消", (dialog, which) -> dialog.cancel());
            builder.show();
        });
    }

    /**
     * ViewPager适配器类
     */
    private class SimpleFragmentAdapter<T> extends PagerAdapter {

        private List<T> mData = new ArrayList<>();

         private SimpleFragmentAdapter(List<T> lists) {
            mData.clear();
            mData.addAll(lists);
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_enlarge_picture, null);
            ImageView img = view.findViewById(R.id.iv_item_enlarge_picture);
            if (mData.get(position) instanceof String) {
                String url = (String) mData.get(position);
                GlideApp.with(mContext).load(url).error(R.mipmap.img_picture_load_failed).into(img);
                if (!isCanDeletePicture) {
                    img.setOnLongClickListener(v -> {
                        savePicture(url, null);
                        return true;
                    });
                }
            } else if (mData.get(position) instanceof BmobFile) {
                BmobFile file = (BmobFile) mData.get(position);
                GlideApp.with(mContext).load(file.getFileUrl()).error(R.mipmap.img_picture_load_failed).into(img);
                if (!isCanDeletePicture) {
                    img.setOnLongClickListener(v -> {
                        savePicture(file.getFileUrl(), file.getFilename());
                        return true;
                    });
                }
            }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    /**
     * 显示保存图片Dialog
     */
    private void savePicture(String url, String fileName) {
        if (mDialog == null) {
            mDialog = new SavePictureDialog(mContext);
        }
        mDialog.setUrl(url, fileName);
        mDialog.show();
    }

    /**
     * 保存图片Dialog类
     */
    public class SavePictureDialog extends RxDialog {

        private String url = null;
        private String fileName = null;

        public SavePictureDialog(Context context) {
            super(context);
            initViewDialog(context);
        }

        public void setUrl(String url, String fileName) {
            this.url = url;
            this.fileName = fileName;
        }

        private void initViewDialog(final Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_save_picture, null);
            TextView tv_save = view.findViewById(R.id.tv_saveToPhone);
            TextView tv_cacel = view.findViewById(R.id.tv_cancelToSave);
            tv_save.setOnClickListener(v -> {
                if (TextUtils.isEmpty(url)) {
                    ToastUtil.show(context, "图片保存失败");
                    cancel();
                    return;
                }
                BmobFile file;
                if (TextUtils.isEmpty(fileName)) {
                    file = new BmobFile(url.substring(48), "", url);
                } else {
                    file = new BmobFile(fileName, "", url);
                }
                File saveFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), file.getFilename());
                LoggerUtil.e(file.getFilename());
                file.download(saveFile, new DownloadFileListener() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            ToastUtil.show(context, "图片已经保存到目录: " + s);
                            // MediaScanner 扫描更新图库图片
                            MediaScannerConnection.scanFile(context, new String[]{s}, null, null);
                        } else {
                            ToastUtil.show(context, e.getMessage());
                        }
                        cancel();
                    }

                    @Override
                    public void onProgress(Integer integer, long l) {

                    }
                });
            });

            tv_cacel.setOnClickListener(v -> cancel());
            setContentView(view);
            mLayoutParams.gravity = 80;
        }
    }

    public interface OnDeletePictureOnClickListener {
        /**
         * 删除图片的位置
         *
         * @param position 在List中的序号
         */
        void isDeleteListener(int position);
    }
}
