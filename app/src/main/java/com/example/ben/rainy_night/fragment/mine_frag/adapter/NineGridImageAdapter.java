package com.example.ben.rainy_night.fragment.mine_frag.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.R;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;

import java.util.List;

/**
 *
 * @author Ben
 * @date 2018/3/1
 */

public class NineGridImageAdapter extends NineGridImageViewAdapter<String> {
    @Override
    protected void onDisplayImage(Context context, ImageView imageView, String s) {
        GlideApp.with(context).load(s).error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).into(imageView);
    }

    @Override
    protected void onItemImageClick(Context context, ImageView imageView, int index, List<String> list) {
        super.onItemImageClick(context, imageView, index, list);
        Toast.makeText(context, "点击了第"+index+"张图片", Toast.LENGTH_SHORT).show();
    }
}
