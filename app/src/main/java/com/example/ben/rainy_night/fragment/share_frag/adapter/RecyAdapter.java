package com.example.ben.rainy_night.fragment.share_frag.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ben.rainy_night.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ben
 * @date 2018/1/17
 */

public class RecyAdapter extends RecyclerView.Adapter<RecyAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<String> mList=new ArrayList<String>();

    public RecyAdapter(Context context) {
        this.mInflater=LayoutInflater.from(context);
    }

    public void addData(List<String> lists){
        this.mList.clear();
        this.mList.addAll(lists);
        notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=  mInflater.inflate(R.layout.recycler_item_share,parent,false);
        ViewHolder holder =new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_recycler_item);
        }
    }
}
