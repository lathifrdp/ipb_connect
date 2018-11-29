package com.example.lathifrdp.demoapp.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.model.Job;

import java.util.ArrayList;
import java.util.List;

public class VacancyPostList extends ArrayAdapter<Job> implements View.OnClickListener {
    private List<Job> dataSet;
    Context mContext;

    public void setList(List<Job> list) {
        if(dataSet==null) dataSet=new ArrayList<>();
        this.dataSet = list;
    }
    public void addList(List<Job> list) {
        if(dataSet==null) dataSet=new ArrayList<>();
        this.dataSet.addAll(list);
    }

    // View lookup cache
    private static class ViewHolder {
        TextView txtTitle;
        TextView txtSubject;
        TextView txtCompany;
        ImageView edit;
        ImageView delete;
    }

    public VacancyPostList(List<Job> data, Context context) {
        super(context, R.layout.row_item_vc_post, data);
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
        Job dataModel=(Job)object;

        switch (v.getId())
        {
            case R.id.edit_vc:
                Snackbar.make(v, "Edit ", Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
            case R.id.delete_vc:
                Snackbar.make(v, "Delete ", Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Job dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_vc_post, parent, false);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.title_vc);
            viewHolder.txtSubject = (TextView) convertView.findViewById(R.id.subject_vc);
            viewHolder.txtCompany = (TextView) convertView.findViewById(R.id.company_vc);
            viewHolder.edit = (ImageView) convertView.findViewById(R.id.edit_vc);
            viewHolder.delete = (ImageView) convertView.findViewById(R.id.delete_vc);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtTitle.setText(dataModel.getTitle());
        viewHolder.txtSubject.setText(dataModel.getSubject());
        viewHolder.txtCompany.setText(dataModel.getCompany());
        viewHolder.edit.setOnClickListener(this);
        viewHolder.edit.setTag(position);
        viewHolder.delete.setOnClickListener(this);
        viewHolder.delete.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}
