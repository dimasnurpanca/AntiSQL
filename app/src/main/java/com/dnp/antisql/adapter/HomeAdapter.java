package com.dnp.antisql.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dnp.antisql.R;

import java.util.List;


public class HomeAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;

    public HomeAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            final LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.gridview_layout, null);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.android_gridview_image);
        TextView textView = (TextView) convertView.findViewById(R.id.android_gridview_text);

        textView.setText(list.get(position));
        switch (position) {
            case 0:
                imageView.setImageResource(R.mipmap.ic_launcher);
                break;
            case 1:
                imageView.setImageResource(R.mipmap.ic_launcher);
                break;
            case 2:
                imageView.setImageResource(R.mipmap.ic_launcher);
                break;
            case 3:
                imageView.setImageResource(R.mipmap.ic_launcher);
                break;

        }
            return convertView;
        }
    }

