package com.example.lathifrdp.demoapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.model.JobLocation;
import com.example.lathifrdp.demoapp.model.StudyProgram;

import java.util.List;


public class JobLocationSpinner extends ArrayAdapter<JobLocation>{

    LayoutInflater flater;
    private List<JobLocation> list;

    public JobLocationSpinner(Activity context, int resouceId, int textviewId, List<JobLocation> list){
        super(context,resouceId,textviewId, list);
        this.list = list;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return rowview(convertView,position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return rowview(convertView,position);
    }

    public JobLocation getItem(int pos){
        if (list != null){
            return list.get(pos);
        }
        return null;
    }

    private View rowview(View convertView , int position){

        JobLocation jobLocation = getItem(position);

        viewHolder holder ;
        View rowview = convertView;
        if (rowview==null) {

            holder = new viewHolder();
            flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(R.layout.spinner_job_location, null, false);

            holder.tvTitle = (TextView) rowview.findViewById(R.id.location_sp);
            rowview.setTag(holder);
        }else{
            holder = (viewHolder) rowview.getTag();
        }

        if (jobLocation != null && !TextUtils.isEmpty(jobLocation.getName()))
            holder.tvTitle.setText(jobLocation.getName());

        return rowview;
    }

    private class viewHolder{
        TextView tvTitle;
    }

}
