package com.dnp.antisql.adapter;


import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dnp.antisql.R;
import com.dnp.antisql.retrofit.IPList;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogAdapter extends ArrayAdapter<IPList> implements View.OnClickListener{

    private ArrayList<IPList> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtType;
        TextView txtStatus;
    }

    public LogAdapter(ArrayList<IPList> data, Context context) {
        super(context, R.layout.ip_row, data);

        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        IPList dataModel=(IPList)object;

        switch (v.getId())
        {
            //     case R.id.item_info:
            //        Snackbar.make(v, "Release date " +dataModel.getDate(), Snackbar.LENGTH_LONG)
            //                .setAction("No action", null).show();
            //        break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        IPList dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.ip_row, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.ip);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.date);
            viewHolder.txtStatus = (TextView) convertView.findViewById(R.id.status);


            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtName.setText(dataModel.getIP());
        viewHolder.txtType.setText(dataModel.getDate());
        viewHolder.txtStatus.setText(dataModel.getStatus());
        if(position%2==1) {
            viewHolder.txtName.setBackgroundResource(R.color.warnaDark);
            viewHolder.txtType.setBackgroundResource(R.color.warnaDark);
            viewHolder.txtStatus.setBackgroundResource(R.color.warnaDark);
        }
        else {
            viewHolder.txtName.setBackgroundResource(R.color.warnaLight);
            viewHolder.txtType.setBackgroundResource(R.color.warnaLight);
            viewHolder.txtStatus.setBackgroundResource(R.color.warnaLight);
        }

        if(dataModel.getStatus().equals("1")){
            viewHolder.txtStatus.setText("Diblokir");
        }
        else if(dataModel.getStatus().equals("2")){
            viewHolder.txtStatus.setText("Aksi");
        }
        else{
            viewHolder.txtStatus.setText("-");
        }

        viewHolder.txtName.setOnClickListener(this);
        viewHolder.txtName.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}