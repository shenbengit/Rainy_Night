package com.example.ben.rainy_night.view;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.R;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.widget.PreviewViewPager;
import com.vondear.rxtools.view.dialog.RxDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 重写RxDialog,用于全屏显示一张图片的Dialog
 *
 * @author Ben
 * @date 2018/2/26
 */

public class EnlargePictureDialog extends RxDialog {

    private List<LocalMedia> images = new ArrayList();
    private PreviewViewPager mViewPage;
    private ImageView mImage;
    private TextView mTitle;
    private ImageButton mDelete;
    private int position;

    private OnDeletePictureOnClickListener mListener;

    /**
     * 是否是只用来查看放大的图片，
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

    public void isCanDeletePicture(boolean isCanDeletePicture) {
        this.isCanDeletePicture = isCanDeletePicture;
    }

    public void setIsDeleteListener(OnDeletePictureOnClickListener listener) {
        this.mListener = listener;
    }

    public void setImagePath(String filePath) {
        initView();
        GlideApp.with(mContext).load(filePath).error(R.mipmap.img_picture_load_failed).into(mImage);
    }

    public void setImageUri(Uri uri) {
        initView();
        GlideApp.with(mContext).load(uri).error(R.mipmap.img_picture_load_failed).into(mImage);
    }


    public void setImageRes(int resId) {
        initView();
        mImage.setImageResource(resId);
    }

    public void setImageBitmap(Bitmap bitmap) {
        initView();
        GlideApp.with(mContext).load(bitmap).error(R.mipmap.img_picture_load_failed).into(mImage);
    }

    public void setImageList(List<LocalMedia> images, int position) {
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
        mViewPage = view.findViewById(R.id.pv_previewPager);
        mImage = view.findViewById(R.id.iv_enlarge_picture);
        if (isCanDeletePicture) {
            mTitle.setVisibility(View.VISIBLE);
            mDelete.setVisibility(View.VISIBLE);
            mViewPage.setVisibility(View.VISIBLE);
            mImage.setVisibility(View.GONE);

        } else {
            mTitle.setVisibility(View.GONE);
            mDelete.setVisibility(View.GONE);
            mViewPage.setVisibility(View.GONE);
            mImage.setVisibility(View.VISIBLE);

        }
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
        setFullScreen();
        setContentView(view);
    }

    /**
     * 设置viewpager
     *
     * @param images
     * @param position
     */
    private void initViewPager(final List<LocalMedia> images, int position) {
        final int[] index = {position};
        mTitle.setText((position +1) + "/" + images.size());
        SimpleFragmentAdapter mAdapter = new SimpleFragmentAdapter(images);
        mViewPage.setAdapter(mAdapter);
        mViewPage.setCurrentItem(position);
        mViewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                index[0] =position;
                mTitle.setText((position +1)+ "/" + images.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("确定删除这张图片吗？")
                        .setPositiveButton("确定", new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mListener.isDeleteListener(index[0]);
                                dialog.cancel();
                                cancel();
                            }
                        })
                        .setNegativeButton("取消", new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.show();
            }
        });
    }

    public class SimpleFragmentAdapter extends PagerAdapter {

        private List<LocalMedia> mData = new ArrayList<LocalMedia>();

        public SimpleFragmentAdapter(List<LocalMedia> lists) {
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
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_enlarge_picture, null);
            ImageView img = view.findViewById(R.id.iv_item_enlarge_picture);
            GlideApp.with(mContext).load(mData.get(position).getCompressPath()).error(R.mipmap.img_picture_load_failed).into(img);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    public interface OnDeletePictureOnClickListener {
        void isDeleteListener(int position);
    }
}
