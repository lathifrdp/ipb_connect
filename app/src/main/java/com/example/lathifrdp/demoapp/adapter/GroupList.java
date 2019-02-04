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
import com.example.lathifrdp.demoapp.model.GroupDiscussion;
import com.example.lathifrdp.demoapp.model.GroupDiscussion;

import java.util.ArrayList;
import java.util.List;

public class GroupList extends ArrayAdapter<GroupDiscussion> implements View.OnClickListener {
    private List<GroupDiscussion> dataSet;
    Context mContext;

    public void setList(List<GroupDiscussion> list) {
        if(dataSet==null) dataSet=new ArrayList<>();
        this.dataSet = list;
    }
    public void addList(List<GroupDiscussion> list) {
        if(dataSet==null) dataSet=new ArrayList<>();
        this.dataSet.addAll(list);
    }

    // View lookup cache
    private static class ViewHolder {
        TextView txtTitle;
        TextView txtDescription;
        TextView txtCreated;
        //ImageView info;
    }

    public GroupList(List<GroupDiscussion> data, Context context) {
        super(context, R.layout.row_item_gd, data);
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
        GroupDiscussion dataModel=(GroupDiscussion)object;

//        switch (v.getId())
//        {
//            case R.id.item_info:
//                Snackbar.make(v, "Total Like: " +dataModel.getTotalLike(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
//                break;
//        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        GroupDiscussion dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_gd, parent, false);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.title_gd);
            viewHolder.txtDescription = (TextView) convertView.findViewById(R.id.desc_gd);
            viewHolder.txtCreated = (TextView) convertView.findViewById(R.id.created_gd);
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

        viewHolder.txtTitle.setText(dataModel.getTitle());
        viewHolder.txtDescription.setText(dataModel.getDescription());
        viewHolder.txtCreated.setText(dataModel.getCreated());
//        viewHolder.info.setOnClickListener(this);
//        viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}
