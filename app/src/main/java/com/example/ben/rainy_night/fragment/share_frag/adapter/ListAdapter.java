package com.example.ben.rainy_night.fragment.share_frag.adapter;

/**
 *
 * @author Ben
 * @date 2018/1/18
 */


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ben.rainy_night.R;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter {

    private List<String> objects = new ArrayList<String>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ListAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setData(List<String> lists){
        objects.clear();
        objects.addAll(lists);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public String getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.recycler_item_share, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((String)getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(String object, ViewHolder holder) {
        //TODO implement

        holder.tvRecyclerItem.setText(object);
    }

    protected class ViewHolder {
        private TextView tvRecyclerItem;

        public ViewHolder(View view) {
            tvRecyclerItem = (TextView) view.findViewById(R.id.tv_recycler_item);
        }
    }
}
