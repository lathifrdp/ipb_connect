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
import com.example.lathifrdp.demoapp.model.Crowdfunding;
import com.example.lathifrdp.demoapp.model.Event;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CrowdAlumniList extends ArrayAdapter<Crowdfunding> implements View.OnClickListener{
    private List<Crowdfunding> dataSet;
    Context mContext;

    public void setList(List<Crowdfunding> list) {
        if(dataSet==null) dataSet=new ArrayList<>();
        this.dataSet = list;
    }
    public void addList(List<Crowdfunding> list) {
        if(dataSet==null) dataSet=new ArrayList<>();
        this.dataSet.addAll(list);
    }

    // View lookup cache
    private static class ViewHolder {
        TextView txtTitle;
        TextView txtCurrent;
        TextView txtTotal;
        TextView txtDescription;
        TextView txtContact;

        ImageView info;
    }

    public CrowdAlumniList(List<Crowdfunding> data, Context context) {
        super(context, R.layout.row_item_cr, data);
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
        Crowdfunding dataModel=(Crowdfunding) object;

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
        Crowdfunding dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        CrowdAlumniList.ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new CrowdAlumniList.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_cr, parent, false);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.title_cr);
            viewHolder.txtDescription = (TextView) convertView.findViewById(R.id.desc_cr);
            viewHolder.txtCurrent = (TextView) convertView.findViewById(R.id.current_cr);
            viewHolder.txtTotal = (TextView) convertView.findViewById(R.id.total_cr);
            //viewHolder.info = (ImageView) convertView.findViewById(R.id.item_info);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CrowdAlumniList.ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;


        int current = Integer.parseInt(dataModel.getCurrentCost());
 //       int total = Integer.parseInt(dataModel.getTotalCost());

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        viewHolder.txtTitle.setText(dataModel.getTitle());
        viewHolder.txtDescription.setText(dataModel.getDescription());
        viewHolder.txtCurrent.setText("Telah terkumpul "+formatRupiah.format((double)current));
 //       viewHolder.txtTotal.setText(" dari target "+formatRupiah.format((double)total));
        //viewHolder.info.setOnClickListener(this);
        //viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}
