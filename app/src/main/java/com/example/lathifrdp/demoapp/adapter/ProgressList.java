package com.example.lathifrdp.demoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.helper.BaseModel;
import com.example.lathifrdp.demoapp.model.Crowdfunding;
import com.example.lathifrdp.demoapp.model.Progress;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProgressList extends ArrayAdapter<Progress> implements View.OnClickListener{
    private List<Progress> dataSet;
    Context mContext;

    public void setList(List<Progress> list) {
        if(dataSet==null) dataSet=new ArrayList<>();
        this.dataSet = list;
    }
    public void addList(List<Progress> list) {
        if(dataSet==null) dataSet=new ArrayList<>();
        this.dataSet.addAll(list);
    }

    // View lookup cache
    private static class ViewHolder {
        TextView txtDescription;
        ImageView gambar;
    }

    public ProgressList(List<Progress> data, Context context) {
        super(context, R.layout.row_item_progress, data);
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
        Progress dataModel=(Progress) object;

//        switch (v.getId())
//        {
//            case R.id.item_info:
//                Snackbar.make(v, "Place: " +dataModel.getPlace(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
//                break;
//        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Progress dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ProgressList.ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ProgressList.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_progress, parent, false);
            viewHolder.txtDescription = (TextView) convertView.findViewById(R.id.desc_cr);
            viewHolder.gambar = (ImageView) convertView.findViewById(R.id.gambar_cr);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ProgressList.ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtDescription.setText(dataModel.getDescription());

        String url = new BaseModel().getCrowdfundingUrl()+dataModel.getFile();
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.placegam)
                .error(R.drawable.placeholdergambar)
                .into(viewHolder.gambar);
        //viewHolder.info.setOnClickListener(this);
        //viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}
