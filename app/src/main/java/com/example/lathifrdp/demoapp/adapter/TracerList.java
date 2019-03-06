package com.example.lathifrdp.demoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.model.Job;
import com.example.lathifrdp.demoapp.model.TracerStudy;

import java.util.ArrayList;
import java.util.List;

public class TracerList extends ArrayAdapter<TracerStudy> implements View.OnClickListener {
    private List<TracerStudy> dataSet;
    Context mContext;

    public void setList(List<TracerStudy> list) {
        if(dataSet==null) dataSet=new ArrayList<>();
        this.dataSet = list;
    }
    public void addList(List<TracerStudy> list) {
        if(dataSet==null) dataSet=new ArrayList<>();
        this.dataSet.addAll(list);
    }

    // View lookup cache
    private static class ViewHolder {
        TextView txtJudul;
        TextView txtDeskripsi;
        TextView txtCreated;
    }

    public TracerList(List<TracerStudy> data, Context context) {
        super(context, R.layout.row_item_tracer, data);
        this.dataSet = data;
        this.mContext=context;

    }
    public int getSize(){
        if(dataSet==null) dataSet=new ArrayList<>();
        return dataSet.size();

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        TracerStudy dataModel=(TracerStudy) object;

//        switch (v.getId())
//        {
//            case R.id.item_info:
//                Snackbar.make(v, "Email " +dataModel.getEmail(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
//                break;
//        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        TracerStudy dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_tracer, parent, false);
            viewHolder.txtJudul = (TextView) convertView.findViewById(R.id.judul_ts);
            viewHolder.txtDeskripsi = (TextView) convertView.findViewById(R.id.deskripsi_ts);
            viewHolder.txtCreated = (TextView) convertView.findViewById(R.id.created_ts);
            //viewHolder.info = (ImageView) convertView.findViewById(R.id.item_info);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtJudul.setText(dataModel.getTitle());
        viewHolder.txtDeskripsi.setText(dataModel.getDescription());
        viewHolder.txtCreated.setText(dataModel.getCreated());
//        viewHolder.info.setOnClickListener(this);
//        viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}
