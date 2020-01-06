package com.refresh.pos.ui.setting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.refresh.pos.R;

import java.util.ArrayList;
import java.util.Map;

public class CategorySpinnerAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Map<String, String>> categoryList;
    private LayoutInflater mInflater;

    public CategorySpinnerAdapter(Context context, ArrayList<Map<String, String>> data) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        categoryList = data;
    }
    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.spinner_adapter, parent, false);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.logo = (ImageView) convertView.findViewById(R.id.logo);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        String name = categoryList.get(position).get("name");
        holder.name.setText(name);

        convertView.setTag(holder);

        return convertView;
    }

    public class ViewHolder {
        TextView name;
        ImageView logo;
    }
}
