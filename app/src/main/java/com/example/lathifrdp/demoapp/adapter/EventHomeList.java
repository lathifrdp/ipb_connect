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
import com.example.lathifrdp.demoapp.model.Event;
import com.example.lathifrdp.demoapp.model.News;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventHomeList extends ArrayAdapter<Event> implements View.OnClickListener{
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
        TextView judul;
        TextView harga;
        TextView tanggal;
        ImageView gambar;
    }

    public EventHomeList(List<Event> data, Context context) {
        super(context, R.layout.row_item_event_home, data);
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
        News dataModel=(News)object;

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
        EventHomeList.ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new EventHomeList.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_event_home, parent, false);
            viewHolder.judul = (TextView) convertView.findViewById(R.id.judul_event_home);
            viewHolder.harga = (TextView) convertView.findViewById(R.id.harga_event_home);
            viewHolder.tanggal = (TextView) convertView.findViewById(R.id.tanggal_event_home);
            viewHolder.gambar = (ImageView) convertView.findViewById(R.id.gambar_event_home);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (EventHomeList.ViewHolder) convertView.getTag();
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

        int harganya = Integer.parseInt(dataModel.getPrice());

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        viewHolder.judul.setText(dataModel.getTitle());
        if(harganya==0){
            viewHolder.harga.setText("Tanpa biaya");
        }
        else{
            viewHolder.harga.setText("Biaya: "+formatRupiah.format((double)harganya));
        }
        viewHolder.tanggal.setText(formattedDate);

        String url = new BaseModel().getEventUrl()+dataModel.getPicture();
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
