package com.example.ben.rainy_night.fragment.mine_frag.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.util.ConstantUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ben
 * @date 2018/2/22
 */
public class PostStoryAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mList=new ArrayList<String>();
    private LayoutInflater inflater;

    public PostStoryAdapter(Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    public void setData(List<String> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        //return mList.size() + 1;//因为最后多了一个添加图片的ImageView
        int count = mList == null ? 1 : mList.size() + 1;
        if (count > ConstantUtil.MAX_PICTURES) {
            return mList.size();
        } else {
            return count;
        }
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.item_post_story, parent, false);
        ImageView iv = (ImageView) convertView.findViewById(R.id.iv_item_post_story);
        if (position < mList.size()) {
            //代表+号之前的需要正常显示图片
            //图片路径
            String picUrl = mList.get(position);
            GlideApp.with(mContext).load(picUrl).error(R.mipmap.img_picture_load_failed).into(iv);
        } else {
            //最后一个显示加号图片
            iv.setImageResource(R.mipmap.img_add_picture);
        }
        return convertView;
    }
}
