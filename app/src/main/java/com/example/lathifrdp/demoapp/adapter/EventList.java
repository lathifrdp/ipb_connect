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
import com.example.lathifrdp.demoapp.model.Event;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventList extends ArrayAdapter<Event> implements View.OnClickListener{
    private List<Event> dataSet;
    Context mContext;

    public void setList(List<Event> list) {
        if(dataSet==null) dataSet=new ArrayList<>();
        this.dataSet = list;
    }
    public void addList(List<Event> list) {
        if(dataSet==null) dataSet=new ArrayList<>();
        this.dataSet.addAll(list);
    }

    // View lookup cache
    private static class ViewHolder {
        TextView txtTitle;
        TextView txtPrice;
        TextView txtDescription;
        TextView txtContact;
        ImageView info;
    }

    public EventList(List<Event> data, Context context) {
        super(context, R.layout.row_item_ev, data);
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
        Event dataModel=(Event)object;

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
        Event dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        EventList.ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new EventList.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_ev, parent, false);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.title_ev);
            viewHolder.txtDescription = (TextView) convertView.findViewById(R.id.desc_ev);
            viewHolder.txtPrice = (TextView) convertView.findViewById(R.id.price_ev);
            viewHolder.info = (ImageView) convertView.findViewById(R.id.item_info);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (EventList.ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = inputFormat.parse(dataModel.getStartDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = outputFormat.format(date);

        int number = Integer.parseInt(dataModel.getPrice());
        String str = NumberFormat.getNumberInstance(Locale.US).format(number);
        viewHolder.txtTitle.setText(dataModel.getTitle());
        viewHolder.txtDescription.setText("Rp "+str);
        viewHolder.txtPrice.setText(formattedDate);
        //viewHolder.info.setOnClickListener(this);
        //viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}
